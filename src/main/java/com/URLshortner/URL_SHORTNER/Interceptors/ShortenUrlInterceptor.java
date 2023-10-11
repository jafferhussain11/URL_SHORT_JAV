package com.URLshortner.URL_SHORTNER.Interceptors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class ShortenUrlInterceptor implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestWrapperBody = new GetRequestBody(request).getBody();
        boolean valid = true;
        String longUrl = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(requestWrapperBody);
            if(jsonNode.isEmpty()){
                valid = false;
            }else{
                longUrl=jsonNode.get("longUrl").asText();
                if(longUrl.isEmpty()){
                    valid = false;
                }

            }
            request.setAttribute("url", longUrl);
            request.setAttribute("valid", valid);

        }
        catch (Exception e){
            throw new Exception("Error in parsing JSON");
        }
        return true;
    }

    class GetRequestBody{

        private String body;

        public GetRequestBody(HttpServletRequest req){

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = null;

            try {
                InputStream inputStream = req.getInputStream();
                if(inputStream !=null){
                    reader = new BufferedReader(new InputStreamReader(inputStream)); //this is the trick?
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = reader.read(charBuffer)) > 0) {  //read into chaBuffer array and put into string builder
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }

                }else{
                    stringBuilder.append("");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            body = stringBuilder.toString();

        }
        public String getBody(){
            return body;
        }
    }


}
