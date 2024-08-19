package com.clp.stexample.common.enums.devices;

import com.clp.stexample.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Preferences implements ApiEndpoint {

    LIST_ALL_PREFERENCES("List all Preferences", "/devicepreferences", GET),
    CREATE_A_PREFERENCES("Create a Preferences", "/devicepreferences", POST),
    GET_PREFERENCE_BY_ID("Get Preference by ID", "/devicepreferences/{preferenceId}", GET),
    DELETE_A_DEVICE_PROFILE("Delete a Device Profile", "/deviceprofiles/{deviceProfileId}", DELETE),
    UPDATE_A_DEVICE_PROFILE("Update a Device Profile", "/deviceprofiles/{deviceProfileId}", PUT),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Preferences(String title, String url, HttpMethod method) {
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
