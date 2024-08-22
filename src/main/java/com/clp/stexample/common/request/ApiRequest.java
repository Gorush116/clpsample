package com.clp.stexample.common.request;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public interface ApiRequest<T> {

    HttpMethod getMethod();

    String getUrl();

    Object getBody();

    Class<T> getResponseType();

    default HttpHeaders getHeaders() {
        return new HttpHeaders();
    }


}
