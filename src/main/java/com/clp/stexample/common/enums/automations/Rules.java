package com.clp.stexample.common.enums.automations;

import com.clp.stexample.common.enums.ApiEndpoint;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

public enum Rules implements ApiEndpoint {

    LIST_RULES("List Rules", "/rules", GET),
    CREATE_A_RULE("Create a Rule", "/rules", POST),
    GET_A_RULE("Get a Rule", "/rules/{ruleId}", GET),
    UPDATE_A_RULE("Update a Rule", "/rules/{ruleId}", PUT),
    DELETE_A_RULE("Delete a Rule", "/rules/{ruleId}", DELETE),
    EXECUTE_A_RULE("Execute a Rule", "/rules/execute/{ruleId}", POST),
    ;

    private final String title;
    private final String url;
    private final HttpMethod method;

    Rules(String title, String url, HttpMethod method) {
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
