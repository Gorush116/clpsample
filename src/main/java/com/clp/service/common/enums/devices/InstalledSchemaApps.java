package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum InstalledSchemaApps implements ApiEndpoint {

    GET_DEVICES_BY_INSTALLEDSCHEMAAPP_ID("Get Devices By InstalledSchemaApp ID", "/schema/installedapps/{isaId}", GET),
    DELETE_AN_INSTALLEDSCHEMAAPP_ID_AND_ASSOCIATED_DEVICES("Delete an InstalledSchemaApp ID and Associated Devices", "/schema/installedapps/{isaId}", DELETE),
    GET_INSTALLEDSCHEMAAPP_BY_LOCATION_ID("Get InstalledSchemaApp By Location ID", "/schema/installedapps/location/{locationId}", GET),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    InstalledSchemaApps(String title, String url, HttpMethod method) {
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
