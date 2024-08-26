package com.clp.service.common.enums;

import org.springframework.http.HttpMethod;

public interface ApiEndpoint {
    String getTitle();
    String getUrl();
    HttpMethod getMethod();
}