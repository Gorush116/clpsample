package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum HubDevices implements ApiEndpoint {

    RETRIEVES_THE_LIST_OF_DRIVER_CHANNELS("Retrieves the list of driver channels", "/hubdevices/{hubDeviceId}/channels", GET),
    RETRIEVES_THE_LIST_OF_HUB_INSTALLED_DRIVER("Retrieves the list of hub installed driver", "/hubdevices/{hubDeviceId}/drivers", GET),
    UPDATE_DEVICE_RECORD_FOR_HUB_DEVICE("Update Device Record For Hub Device", "/hubdevices/{hubDeviceId}/drivers/{driverId}", PATCH),
    INSTALL_DRIVERS("Install Drivers", "/hubdevices/{hubDeviceId}/drivers/{driverId}", PUT),
    UNINSTALL_DRIVERS("Uninstall Drivers", "/hubdevices/{hubDeviceId}/drivers/{driverId}", DELETE),
    GET_DRIVER_IN_USE_BY_HUB_DEVICE("Get Driver in use by Hub Device", "/hubdevices/{hubDeviceId}/drivers/{driverId}", GET),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    HubDevices(String title, String url, HttpMethod method) {
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
