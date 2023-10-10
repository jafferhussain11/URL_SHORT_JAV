package com.URLshortner.URL_SHORTNER.Services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
@Component
public class FileReadWriteService {

    private static BufferedReader br = null;
    private static FileWriter fw = null;

    @PostConstruct
    public void initialize() {
        try {


            br = new BufferedReader(new FileReader("src/main/resources/counter.txt"));
            fw = new FileWriter("src/main/resources/counter.txt", true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long readCounter() {
        try {
            int i;
            String line;
            String s = "";
            while ((line= br.readLine()) != null) {
                s=line;
            }
            if(s.equals("")) {
                return 0;
            }
            return Long.parseLong(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public long readCounter() {
//        try {
//            int i;
//            String s ="";
//            while ((i = fr.read()) != -1) {
//                s = String.valueOf((char) i);
//            }
//            if(s.equals("")) {
//                return 0;
//            }
//            return Long.parseLong(s);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void writeCounter(long counter) {
        try {
            //write data in new line every time
            fw.write(String.valueOf(counter));
            fw.write(System.lineSeparator());
            fw.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
