package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Devices implements ApiEndpoint {

    LIST_DEVICES("List Devices", "/devices", GET),
    INSTALL_A_DEVICE("Install a Device", "/devices", POST),
    GET_THE_DESCRIPTION_OF_A_DEVICE("Get the Description of a Device", "/devices/{deviceId}", GET),
    DELETE_A_DEVICE("Delete a Device", "/devices/{deviceId}", DELETE),
    UPDATE_A_DEVICE("Update a Device", "/devices/{deviceId}", PUT),
    EXECUTE_COMMANDS_ON_A_DEVICE("Execute Commands on a Device", "/devices/{deviceId}/commands", POST),
    CREATE_DEVICE_EVENTS("Create Device Events", "/devices/{deviceId}/commands", POST),
    GET_THE_FULL_STATUS_OF_A_DEVICE("Get the Full Status of a Device", "/devices/{deviceId}/status", GET),
    GET_THE_STATUS_OF_A_DEVICE_COMPONENT("Get the Status of a Device Component", "/devices/{deviceId}/components/{componentId}/status", GET),
    GET_THE_STATUS_OF_A_CAPABILITY("Get the Status of a Capability", "/devices/{deviceId}/components/{componentId}/capabilities/{capabilityId}/status", GET),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Devices(String title, String url, HttpMethod method) {
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
