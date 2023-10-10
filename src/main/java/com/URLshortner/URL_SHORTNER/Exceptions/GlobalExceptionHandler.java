package com.URLshortner.URL_SHORTNER.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){

       ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage(), request.getRequestURL().toString());
       return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    class ErrorResponse{
        private int status;
        private String message;
        private String url;

        public ErrorResponse() {
        }

        public ErrorResponse(int status, String message, String url) {
            this.status = status;
            this.message = message;
            this.url = url;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getUrl() {
            return url;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
