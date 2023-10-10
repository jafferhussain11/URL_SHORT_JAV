package com.URLshortner.URL_SHORTNER.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;


public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final String requestBody;

    public CustomHttpServletRequestWrapper(HttpServletRequest request, String requestBody) {
        super(request);
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }
}
