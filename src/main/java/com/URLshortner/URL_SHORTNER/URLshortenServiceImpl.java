package com.URLshortner.URL_SHORTNER;

import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
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

    static long counter;
    private static String base62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @PostConstruct
    public void initialize() {
        long readCount= fileReadWriteService.readCounter();
        System.out.println("readCount: "+readCount);
        if(readCount == 0) {
            counter = 1;
            fileReadWriteService.writeCounter(counter);
        } else {
            counter = readCount;
        }

    }
    @Override
    public String shortenUrl(String longUrl) {

        String shortUrl = convertToBase62();
        //write to csv
        String[] record = {shortUrl,longUrl};
        try {
            //check if long url already present
            if(!csvReaderService.checkIfLongUrlPresent(longUrl)){
                csvWriterService.writeDataToCsv(record); //
            }else{
                return null;
            }
            counter++;
            fileReadWriteService.writeCounter(counter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //System.out.println("Shortened URL: "+ shortUrl + " Long URL: "+longUrl + " Counter: "+ counter);
        return shortUrl;



    }

    @Override
    public String updateShortUrl(String shortUrl, String longUrl) {

        //search on shorturl and update correspondng long URL
        try {
            String[] temp = csvReaderService.getCsvRecord(shortUrl);
            System.out.println("temp: "+temp[0] + " " + temp[1]);
            if(!Objects.equals(temp[0], "")){
                //update long url in csv without creating new record
                deleteCsvRecord(shortUrl);
                temp[1] = longUrl;
                csvWriterService.writeDataToCsv(temp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public String getLongUrl(String shortUrl) {

        String longUrl = null;
        try {
            String[] temp = csvReaderService.getCsvRecord(shortUrl);
            longUrl = temp[1];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return longUrl;
    }

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

    public void deleteCsvRecord(String shortUrl){

        // delete record from csv
        try {
            List<String[]> records = csvReaderService.getAllRecords();
            for(String[] record: records){
                if(Objects.equals(record[0], shortUrl)){
                    records.remove(record);
                    break;
                }
            }
            //write to csv
            csvWriterService.writeAllDataToCsv(records);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
