package com.clp.stexample.common.enums.locations;

import com.clp.stexample.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Modes implements ApiEndpoint {

    GET_THE_MODES_A_LOCATION("Get the Modes of a Location", "/locations/{locationId}/modes", GET),
    CREATE_A_MODE("Create a Mode", "/locations/{locationId}/modes", POST),
    GET_A_MODE("Get a Mode", "/locations/{locationId}/modes/{modeId}", GET),
    DELETE_A_MODE("Delete a Mode", "/locations/{locationId}/modes/{modeId}", DELETE),
    UPDATE_A_MODE_LABEL("Update a Mode's Label", "/locations/{locationId}/modes/{modeId}", PUT),
    GET_A_LOCATION_CURRENT_MODE("Get a Location's Current Mode", "/locations/{locationId}/modes/current", GET),
    CHANGE_A_LOCATION_CURRENT_MODE("Change a Location's Current Mode", "/locations/{locationId}/modes/current", PUT),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Modes(String title, String url, HttpMethod method) {
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
