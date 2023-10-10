package com.URLshortner.URL_SHORTNER.Services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Component
public class URLshortenServiceImpl implements URLshortenService {

    @Autowired
    private CsvWriterService csvWriterService;
    @Autowired
    private CsvReaderService csvReaderService;
    @Autowired
    private FileReadWriteService fileReadWriteService;
    @Autowired
    private UrlMapService urlMapService;
    static long counter;
    private static final String base62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    @PostConstruct
    public void initialize() {
        //read counter from file
        long readCount= fileReadWriteService.readCounter();
        if(readCount == 0) {
            counter = 1000000000L;
            fileReadWriteService.writeCounter(counter);
        }else {
            counter = readCount;
            //init map
            try{
                List<String[]> records = csvReaderService.getAllRecords();
                urlMapService.insertAllinMap(records);
            } catch (IOException e) {
                throw new RuntimeException(e);
                }
        }
    }

    @Override
    public String shortenUrl(String longUrl) {

        String shortUrl = convertToBase62();
        //write to csv
        String[] record = {shortUrl,longUrl};
        try {
            //check if long url already present
            if(!urlMapService.checkIfLongUrlPresent(longUrl)){
                csvWriterService.writeDataToCsv(record);
                //write record in map
                urlMapService.insertOneInMap(shortUrl,longUrl);
            }else{
                System.out.println("duplicate long url");
                return null;
            }
            counter++;
            fileReadWriteService.writeCounter(counter);

        } catch (IOException e) {
            throw new RuntimeException(e);
            }
        return shortUrl;
    }


    @Override
    public String getLongUrl(String shortUrl) {
        return urlMapService.getLongUrl(shortUrl);
    }

    //to do
    @Override
    public String updateExpiry(String shortUrl, Integer days) {
        return null;
    }


    public static String convertToBase62() {

        long count = counter;
        StringBuilder shortUrl= new StringBuilder();
        while(count>0){
            int rem = (int) (count%62);
            shortUrl.append(base62.charAt(rem));
            count/=62;
        }
        shortUrl.reverse();
        return shortUrl.toString();
    }

    @Override
    public boolean updateShortUrl(String shortUrl, String updatedLongUrl) {

        //search on shorturl and update correspondng long URL
        if (urlMapService.checkIfShortUrlPresent(shortUrl) && !urlMapService.checkIfLongUrlPresent(updatedLongUrl)) {
                try {
                    deleteCsvRecord(shortUrl);
                    csvWriterService.writeDataToCsv(new String[]{shortUrl, updatedLongUrl});
                    urlMapService.insertOneInMap(shortUrl, updatedLongUrl);
                    return true;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
        }
        return false;
    }

    //to be used in updateShortUrl and deleteShortUrl
    public void deleteCsvRecord (String shortUrl){
        // delete record from csv
        try {
            List<String[]> records = UrlMapService.getRecords();
                for (String[] record : records) {
                    if (Objects.equals(record[0], shortUrl)) {
                        records.remove(record);
                        //remove from map
                        urlMapService.deleteOneFromMap(shortUrl);
                        csvWriterService.writeAllDataToCsv(records);
                        break;
                    }
                }
        }catch(IOException e){
                    throw new RuntimeException(e);
            }
    }

}