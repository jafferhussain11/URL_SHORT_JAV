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

    public String[] getCsvRecord(String shortUrl) throws IOException {

            //String[] ans = null;
            String[] temp;
            while((temp=reader.readNext())!=null){

                 if(Objects.equals(temp[0], shortUrl)){
                     return temp;
                 }
            }
            return temp;
    }

    public boolean checkIfLongUrlPresent(String longUrl){

        String[] temp;
        while (true) {

            try {
                if (((temp=reader.readNext())!=null)) {

                    if (Objects.equals(temp[1], longUrl)) {
                        return true;
                    }
                }else {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;

    }

    public List<String[]> getAllRecords() throws IOException {
        return reader.readAll();
    }
}
