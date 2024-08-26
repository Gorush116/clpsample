package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Profiles implements ApiEndpoint {

    CREATE_A_DEVICE_PROFILE("Create a Device Profile", "/deviceprofiles", POST),
    LIST_ALL_DEVICE_PROFILES_FOR_A_USER("List all Device Profiles for a User", "/deviceprofiles", GET),
    GET_A_DEVICE_PROFILE("Get a Device Profile", "/deviceprofiles/{deviceProfileId}", GET),
    DELETE_A_DEVICE_PROFILE("Delete a Device Profile", "/deviceprofiles/{deviceProfileId}", DELETE),
    UPDATE_A_DEVICE_PROFILE("Update a Device Profile", "/deviceprofiles/{deviceProfileId}", PUT),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Profiles(String title, String url, HttpMethod method) {
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
