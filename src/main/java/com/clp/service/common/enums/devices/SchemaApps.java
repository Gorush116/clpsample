package com.clp.service.common.enums.devices;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum SchemaApps implements ApiEndpoint {

    GET_APPS_FOR_A_GIVEN_ACCESS_TOKEN("Get Apps For a Given Access Token", "/schema/apps", GET),
    CREATE_A_SCHEMA_APP("Create a Schema App", "/schema/apps", POST),
    GET_SCHEMA_APP_INFO("Get Schema App Info", "/schema/apps/{endpointAppId}", GET),
    UPDATE_SCHEMA_APP_INFO("Update Schema App Info", "/schema/apps/{endpointAppId}", PUT),
    DELETE_SCHEMA_APP_INFO("Delete Schema App Info", "/schema/apps/{endpointAppId}", DELETE),
    GET_SCHEMA_APPS_BY_USERID("Get Schema Apps By userId", "/schema/apps/user/{userId}", GET),
    CREATE_AN_INVITATION_FOR_A_SCHEMA_APP("Create an Invitation for a Schema App", "/invites/schemaApp", POST),
    GET_A_LIST_OF_INVITATIONS_FOR_A_SCHEMA_APP("Get a List of Invitations for a Schema App", "/invites/schemaApp", GET),
    CHECK_THE_ACCEPTANCE_STATUS_OF_A_SCHEMA_APP_INVITATION("Check the Acceptance Status of a Schema App Invitation", "/invites/schemaApp/checkAcceptance", GET),
    ACCEPT_A_SCHEMA_APP_INVITATION("Accept a Schema App Invitation", "/invites/schemaApp/{ShortCode}/accept", GET),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    SchemaApps(String title, String url, HttpMethod method) {
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
