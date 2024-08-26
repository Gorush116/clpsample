package com.clp.service.common.enums.locations;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Rooms implements ApiEndpoint {

    LIST_ROOMS("List Rooms", "/locations/{locationId}/rooms", GET),
    CREATE_A_ROOM("Create a Room", "/locations/{locationId}/rooms", POST),
    GET_A_ROOM("Get a Room", "/locations/{locationId}/rooms/{roomId}", GET),
    UPDATE_A_ROOM("Update a Room", "/locations/{locationId}/rooms/{roomId}", PUT),
    DELETE_A_ROOM("Delete a Room", "/locations/{locationId}/rooms/{roomId}", DELETE);

    private final String title;
    private final String url;
    private final HttpMethod method;

    Rooms(String title, String url, HttpMethod method) {
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
