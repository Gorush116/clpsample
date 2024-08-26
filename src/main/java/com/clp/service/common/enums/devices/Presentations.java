package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Presentations implements ApiEndpoint {

    GENERATE_DEVICE_CONFIGURATION_FROM_A_DEVICE_PROFILE_OR_DTH("Generate Device Configuration from a Device Profile or DTH", "/presentation/types/{typeIntegrationId}/deviceconfig", GET),
    CREATE_A_DEVICE_CONFIGURATION("Create a Device Configuration", "/presentation/deviceconfig", POST),
    GET_A_DEVICE_CONFIGURATION("Get a Device Configuration", "/presentation/deviceconfig", GET),
    GET_A_DEVICE_PRESENTATION("Get a Device Presentation", "/presentation", GET),
    CREATE_A_CAPABILITY_PRESENTATION("Create a Capability Presentation", "/capabilities/{capabilityId}/{capabilityVersion}/presentation", POST),
    GET_A_CAPABILITY_PRESENTATION_BY_ID_AND_VERSION("Get a Capability Presentation by ID and Version", "/capabilities/{capabilityId}/{capabilityVersion}/presentation", GET),
    UPDATE_A_CAPABILITY_PRESENTATION("Update a Capability Presentation", "/capabilities/{capabilityId}/{capabilityVersion}/presentation", PUT),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Presentations(String title, String url, HttpMethod method) {
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
