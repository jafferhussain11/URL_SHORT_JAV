//package com.URLshortner.URL_SHORTNER;
//
//import com.opencsv.CSVWriter;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Component;
//
//
//import java.io.FileWriter;
//import java.io.IOException;
//
//@Component
//public class CsvWriterService {
//
//    private CSVWriter writer;
//
//    @PostConstruct
//    public void initialize() throws IOException {
//        System.out.println("Inside CSVWriterService initialize method");
//        writer = new CSVWriter(new FileWriter("src/main/resources/urls.csv", true)); // Use 'true' to append to the existing file
//    }
//
//    public void writeDataToCsv(String[] record) throws IOException {
//        if (writer != null) {
//            writer.writeNext(record);
//            writer.flush(); // Flush the data to the file
//        }
//    }
//
//    public void close() throws IOException {
//        if (writer != null) {
//            writer.close(); // Close the CSVWriter when done
//        }
//    }
//}


package com.URLshortner.URL_SHORTNER.Services;

import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvWriterService {

    //create a single instance of CSVWriter
    private static CSVWriter writer = null;

    @PostConstruct
    void init() throws IOException {
        //initialize CSVWriter
        writer = new CSVWriter(new FileWriter("src/main/resources/urls.csv", true));
    }

    public void writeDataToCsv(String[] record) throws IOException {
        //write data to CSV
        writer.writeNext(record);
        writer.flush();


    }
    public void writeAllDataToCsv(List<String[]> records) throws IOException {

        //clear the file before writing
        writer = new CSVWriter(new FileWriter("src/main/resources/urls.csv", false));

        //write data to CSV
        for(String[] record : records){
            writer.writeNext(record);
        }
        writer.flush();
    }

}
