package com.clp.stexample.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public enum HTTPErrorCode {
    BAD_REQUEST(400, "Bad Request", "The client has issued an invalid request."),
    UNAUTHORIZED(401, "Unauthorized", "Authorization for the API is required."),
    FORBIDDEN(403, "Forbidden", "The request has been authenticated but does not have appropriate permissions."),
    NOT_FOUND(404, "Not Found", "The requested path does not exist."),
    NOT_ACCEPTABLE(406, "Not Acceptable", "The client has requested a MIME type not supported by the server."),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type", "The contentType header is not supported by the server."),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity", "The server cannot process the valid request."),
    TOO_MANY_REQUESTS(429, "Too Many Requests", "The client has exceeded the number of requests allowed."),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "An unexpected error has occurred."),
    NOT_IMPLEMENTED(501, "Not Implemented", "The requested feature has yet to be implemented."),
    UNDEFINED_ERROR(40001, "Undefined Error", "Undefined error occurred."),;

    private final int statusCode;
    private final String name;
    private final String description;

    HTTPErrorCode(int statusCode, String name, String description) {
        this.statusCode = statusCode;
        this.name = name;
        this.description = description;
    }

    public static HTTPErrorCode fromHttpStatus(HttpStatusCode status) {
        for (HTTPErrorCode errorCode : values()) {
            if (errorCode.getStatusCode() == status.value()) {
                return errorCode;
            }
        }
        return HTTPErrorCode.UNDEFINED_ERROR; // or throw an exception
    }
}

