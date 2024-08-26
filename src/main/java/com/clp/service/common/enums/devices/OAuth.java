package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.POST;

public enum OAuth implements ApiEndpoint {

    GENERATE_STCLIENTID_AND_STCLIENTSECRET("Generate stClientId and stClientSecret", "/schema/oauth/stclient/credentials", POST),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    OAuth(String title, String url, HttpMethod method) {
        this.title = title;
        this.url = url;
        this.method = method;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }
}
