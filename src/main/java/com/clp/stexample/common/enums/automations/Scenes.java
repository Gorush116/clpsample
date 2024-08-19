package com.clp.stexample.common.enums.automations;

import com.clp.stexample.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public enum Scenes implements ApiEndpoint {

    LIST_SCENES("List Scenes", "/scenes", GET),
    CREATE_A_LOCATION("Execute a Scene", "/scenes/{sceneId}/execute", POST),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Scenes(String title, String url, HttpMethod method) {
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
