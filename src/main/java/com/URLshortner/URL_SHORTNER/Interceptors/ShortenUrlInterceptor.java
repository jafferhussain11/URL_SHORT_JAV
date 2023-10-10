package com.URLshortner.URL_SHORTNER.Interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ShortenUrlInterceptor implements HandlerInterceptor{
//
//    private ObjectMapper mapper = new ObjectMapper();
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Read the request body into a string
//        String requestBody = readRequestBody(request);
//
//        if (!isValidRequestBody(requestBody)) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
//            response.getWriter().write("Bad Request: Invalid request body");
//            return false;
//        }
//
//        //create a copy of request body as input stream can be read only once and pass it to controller
//        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request, requestBody);
//
//        // Set the request body back onto the request object
//
//        return true;
//    }
//
//    private String readRequestBody(HttpServletRequest request) throws IOException {
//        StringBuilder requestBody = new StringBuilder();
//        BufferedReader reader = request.getReader();
//
//        String line;
//        while ((line = reader.readLine()) != null) {
//            requestBody.append(line);
//        }
//
//        return requestBody.toString();
//    }
//
//    private boolean isValidRequestBody(String requestBody) {
//        // Implement your request body validation logic here
//        return true; // Placeholder, replace with your validation logic
//    }
//


}
