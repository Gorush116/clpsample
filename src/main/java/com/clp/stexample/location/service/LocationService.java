package com.clp.stexample.location.service;

import com.clp.stexample.common.request.ApiRequest;
import com.clp.stexample.common.request.ApiRequestBuilder;
import com.clp.stexample.common.response.ApiResponse;
import com.clp.stexample.common.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final ApiService apiService;

    public ApiResponse getLocationList(Object req) {

        return apiService.execute(ApiRequestBuilder.builder()
                .headers(apiService.createHeaders())                // header
                .method(HttpMethod.GET)                             // HTTP Method
                .url("https://api.smartthings.com/v1/locations")    // url
                .body(req)                                          // request body
                .responseType(Object.class)                         // response type
                .build()
        );
    }
}
