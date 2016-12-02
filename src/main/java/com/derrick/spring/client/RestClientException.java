package com.derrick.spring.client;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Created by Dre on 2016/11/30.
 */
public class RestClientException extends NestedRuntimeException {

    private static final long serialVersionUID = -5724603685679886200L;
    /**
     * http status Code
     */
    private HttpStatus statusCode;
    /**
     * status text
     */
    private String statusText;
    /**
     * response body
     */
    private String responseBody;
    /**
     * response headers
     */
    private HttpHeaders responseHeaders;

    public RestClientException(Exception exception) {
        super(exception.getMessage(), exception);

        if (exception instanceof HttpServerErrorException) {
            HttpServerErrorException e = (HttpServerErrorException) exception;

            this.statusCode = e.getStatusCode();
            this.statusText = e.getStatusText();
            this.responseBody = e.getResponseBodyAsString();
            this.responseHeaders = e.getResponseHeaders();
        } else if (exception instanceof HttpClientErrorException) {
            HttpClientErrorException e = (HttpClientErrorException) exception;

            this.statusCode = e.getStatusCode();
            this.statusText = e.getStatusText();
            this.responseBody = e.getResponseBodyAsString();
            this.responseHeaders = e.getResponseHeaders();
        } else {
            this.statusText = exception.getMessage();
        }
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(HttpHeaders responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
