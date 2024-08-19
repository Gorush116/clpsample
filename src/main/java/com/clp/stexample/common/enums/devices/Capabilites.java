package com.clp.stexample.common.enums.devices;

import com.clp.stexample.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Capabilites implements ApiEndpoint {

    LIST_ALL_CAPABILITIES("List all Capabilities", "/capabilities", GET),
    CREATE_A_CAPABILITY("Create a Capability", "/capabilities", POST),
    LIST_A_CAPABILITY_VERSIONS("List a Capability's Versions", "/capabilities/{capabilityId}", GET),
    GET_A_CAPABILITY_BY_ID_AND_VERSION("Get a Capability by ID and Version", "/capabilities/{capabilityId}/{capabilityVersion}", GET),
    UPDATE_A_CAPABILITY("Update a Capability", "/capabilities/{capabilityId}/{capabilityVersion}", PUT),
    DELETE_A_CAPABILITY_BY_ID_AND_VERSION("Delete a Capability by ID and Version", "/capabilities/{capabilityId}/{capabilityVersion}", DELETE),
    LIST_A_CAPABILITYS_LOCALIZATIONS("List a Capability's Localizations", "/capabilities/{capabilityId}/{capabilityVersion}/i18n", GET),
    CREATE_A_CAPABILITY_LOCALIZATION_FOR_A_LOCALE("Create a Capability Localization for a Locale", "/capabilities/{capabilityId}/{capabilityVersion}/i18n", POST),
    GET_A_CAPABILITY_LOCALIZATION_BY_LOCALE("Get a Capability Localization by Locale", "/capabilities/{capabilityId}/{capabilityVersion}/i18n/{locale}", GET),
    UPDATE_A_CAPABILITY_LOCALIZATION_BY_LOCALE("Update a Capability Localization by Locale", "/capabilities/{capabilityId}/{capabilityVersion}/i18n/{locale}", PUT),
    PATCH_A_CAPABILITY_LOCALIZATION_BY_LOCALE("Patch a Capability Localization by Locale", "/capabilities/{capabilityId}/{capabilityVersion}/i18n/{locale}", PATCH),
    LIST_CAPABILITIES_BY_NAMESPACE("List Capabilities by Namespace", "/capabilities/namespaces/{namespace}", GET),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Capabilites(String title, String url, HttpMethod method) {
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
