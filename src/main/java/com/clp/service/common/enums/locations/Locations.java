package com.clp.service.common.enums.locations;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Locations implements ApiEndpoint {

    LIST_LOCATIONS("List Locations", "/locations", GET),
    CREATE_A_LOCATION("Create a Location", "/locations", POST),
    GET_A_LOCATION("Get a Location", "/locations/{locationId}", GET),
    UPDATE_A_LOCATION("Update a Location", "/locations/{locationId}", PUT),
    PATCH_A_LOCATION("Patch a Location", "/locations/{locationId}", PATCH),
    DELETE_A_LOCATION("Delete a Loacation", "/locations/{locationId}", DELETE);

    private final String title;
    private final String url;
    private final HttpMethod method;

    Locations(String title, String url, HttpMethod method) {
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
