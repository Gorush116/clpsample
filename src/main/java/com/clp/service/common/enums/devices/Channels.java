package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Channels implements ApiEndpoint {

    CREATE_A_CHANNEL("Create a channel", "/distchannels", POST),
    LIST_CHANNELS("List channels", "/distchannels", GET),
    GET_A_CHANNEL_BY_ID("Get a channel by ID", "/distchannels/{channelId}", GET),
    UPDATE_A_CHANNEL("Update a channel", "/distchannels/{channelId}", PUT),
    DELETE_A_CHANNEL("Delete a channel", "/distchannels/{channelId}", DELETE),
    ASSIGN_DRIVER_TO_CHANNEL("Assign driver to channel", "/distchannels/{channelId}/drivers", POST),
    LIST_DRIVERS_ASSIGNED_TO_CHANNEL("List drivers assigned to channel", "/distchannels/{channelId}/drivers", GET),
    RETRIEVE_A_DRIVER_CHANNEL("Retrieve a driver channel", "/distchannels/{channelId}/drivers/{driverId}", GET),
    UPDATE_DRIVER_CHANNEL_VERSION("Update driver channel version", "/distchannels/{channelId}/drivers/{driverId}", PUT),
    DELETE_DRIVER_CHANNEL("Delete driver channel", "/distchannels/{channelId}/drivers/{driverId}", DELETE),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Channels(String title, String url, HttpMethod method) {
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
