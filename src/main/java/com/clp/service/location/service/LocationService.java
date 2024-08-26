package com.clp.service.location.service;

import com.clp.service.common.response.ApiResponse;
import org.springframework.http.HttpMethod;

import java.util.concurrent.CompletableFuture;

public interface LocationService {

    <T> ApiResponse callApi(HttpMethod method, Object req, Class<T> responseType);

    String callSyncApi();

    CompletableFuture<String> callAsyncApi();

    CompletableFuture<String> test();
}
