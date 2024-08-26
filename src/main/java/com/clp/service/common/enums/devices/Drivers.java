package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Drivers implements ApiEndpoint {

    LIST_DRIVERS("List Drivers", "/drivers", GET),
    GET_CURRENT_DEFAULT_DRIVERS("Get Current Default Drivers", "/drivers/default", GET),
    DELETE_DRIVER("Delete Driver", "/drivers/{driverId}", DELETE),
    GET_DRIVER("Get Driver", "/drivers/{driverId}", GET),
    GET_DRIVER_REVISION("Get Driver Revision", "/drivers/{driverId}/versions/{version}", GET),
    UPLOAD_PACKAGE("Upload Package", "/drivers/package", POST),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Drivers(String title, String url, HttpMethod method) {
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
