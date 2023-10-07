package com.URLshortner.URL_SHORTNER;

import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
public class UrlController {




    @Autowired
    URLshortenService urlShortenService;

    @PostMapping("/shorten/")
    public String shortenUrl(@RequestBody JsonNode jsonNode){

        String longUrl = jsonNode.get("longUrl").asText();
        String resp = urlShortenService.shortenUrl(longUrl);
        if(resp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL already present");
        }else {
            return resp;
        }
    }

    @GetMapping("/get/{shortUrl}")
    public void getLongUrl(@PathVariable String shortUrl, HttpServletResponse response) {


        //redirect to long url

        String redirectLink = urlShortenService.getLongUrl(shortUrl);
        //System.out.println("long: "+redirectLink);
        try {
            if(redirectLink!= null) response.sendRedirect(redirectLink);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL Not Found", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not redirect!", e);
        }

    }

    @PatchMapping("/update")
    public String updateUrl(@RequestBody JsonNode jsonNode){

        String shortUrl = jsonNode.get("shortUrl").asText();
        String longUrl = jsonNode.get("longUrl").asText();
        String resp = urlShortenService.updateShortUrl(shortUrl,longUrl);
        if(resp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL not present");
        }else {
            return resp;
        }
    }

}
