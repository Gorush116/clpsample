package com.clp.stexample.common.enums;

import org.springframework.http.HttpMethod;

public interface ApiEndpoint {
    String getTitle();
    String getUrl();
    HttpMethod getMethod();
}