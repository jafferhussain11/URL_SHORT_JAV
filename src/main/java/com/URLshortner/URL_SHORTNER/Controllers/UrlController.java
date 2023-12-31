package com.URLshortner.URL_SHORTNER.Controllers;

import com.URLshortner.URL_SHORTNER.Services.URLshortenService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
public class UrlController {
    @Autowired
    URLshortenService urlShortenService;


    @PostMapping("/shorten")
    public String shortenUrl(@RequestAttribute String url, @RequestAttribute boolean valid){

        if(!valid){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON cannot be empty or longUrl value must be present!");
        }
        String longUrl = url;
        //call shortenUrl service
        String resp = urlShortenService.shortenUrl(longUrl);
        if(resp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL already present");
        }
        return resp;
    }

    @GetMapping("/get/{shortUrl}")
    public void getLongUrl(@PathVariable String shortUrl, HttpServletResponse response) {

        //validate shortUrl
        if(shortUrl.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL must be present And cannot be null!");
        }

        //redirect to long url
        String redirectLink = urlShortenService.getLongUrl(shortUrl);
        try {
            if(redirectLink!= null) response.sendRedirect(redirectLink);
            else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL Not Found");

        }catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not redirect!", e);
            }

    }

    @PatchMapping("/update")
    public boolean updateUrl(@RequestBody JsonNode jsonNode){

        if(jsonNode.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON is empty!");
        }

        String shortUrl = jsonNode.get("shortUrl").asText();
        String longUrl = jsonNode.get("longUrl").asText();

        if (shortUrl.isEmpty() || longUrl.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL must be present And cannot be null!");
        }

        boolean resp = urlShortenService.updateShortUrl(shortUrl,longUrl);
        if(!resp){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL not present");
        }else{
            return resp;
            }
    }

}
