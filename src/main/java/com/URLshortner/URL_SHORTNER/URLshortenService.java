package com.URLshortner.URL_SHORTNER;

public interface URLshortenService {

    public String shortenUrl(String longUrl);
    public String updateShortUrl(String shortUrl,String longUrl);
    public String getLongUrl(String shortUrl);
    public String updateExpiry(String shortUrl,Integer days);





}
