package com.URLshortner.URL_SHORTNER;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class CsvReaderService {

    private static CSVReader reader = null;

    @PostConstruct
    public void initialize() throws FileNotFoundException {

        reader = new CSVReader(new FileReader("src/main/resources/urls.csv"));
    }

        //fetch data record from  map instead of CSV
//    public String[] getCsvRecord(String shortUrl) throws IOException {
//
//            //String[] ans = null;
//            String[] temp;
//            while((temp=reader.readNext())!=null){
//
//                 if(Objects.equals(temp[0], shortUrl)){
//                     return temp;
//                 }
//            }
//            return temp;
//    }

    public boolean checkIfLongUrlPresent(String longUrl){

        String[] record;
        try {

            while ((record = reader.readNext()) != null) {
                // Check if the second column (index 1) contains the long URL
                if (record.length > 1 && record[1].equals(longUrl)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    public List<String[]> getAllRecords() throws IOException {
        return reader.readAll();
    }
}
