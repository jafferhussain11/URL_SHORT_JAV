package com.URLshortner.URL_SHORTNER.Services;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UrlMapService {

    private static Map<String, String> urlMap = new HashMap<>();

    public void insertAllinMap(List<String[]> records){

        for(String[] record : records){
            urlMap.put(record[0],record[1]);
        }
    }
    public void insertOneInMap(String key,String value){

        urlMap.put(key,value);
    }

    public void deleteOneFromMap(String key){

        urlMap.remove(key);
    }

    public boolean checkIfLongUrlPresent(String longUrl){

        return urlMap.containsValue(longUrl);
    }

    public boolean checkIfShortUrlPresent(String shortUrl){

        return urlMap.containsKey(shortUrl);
    }
    public String getLongUrl(String shortUrl){

        return urlMap.get(shortUrl);
    }

    public static List<String[]> getRecords(){

        List<String[]> records = new ArrayList<>();
        for(Map.Entry<String,String> entry : urlMap.entrySet()){
            String[] record;
            record = new String[2];
            record[0] = entry.getKey();
            record[1] = entry.getValue();
            records.add(record);
        }
        return records;
    }
}
