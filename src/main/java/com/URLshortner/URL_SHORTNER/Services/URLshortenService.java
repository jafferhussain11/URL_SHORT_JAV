package com.URLshortner.URL_SHORTNER.Services;

public interface URLshortenService {

    public String shortenUrl(String longUrl);
    public boolean updateShortUrl(String shortUrl,String longUrl);
    public String getLongUrl(String shortUrl);
    public String updateExpiry(String shortUrl,Integer days);





}
