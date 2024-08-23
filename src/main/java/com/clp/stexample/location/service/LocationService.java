package com.clp.stexample.location.service;

import com.clp.stexample.common.request.ApiRequestBuilder;
import com.clp.stexample.common.response.ApiResponse;
import com.clp.stexample.common.service.ApiService;
import com.clp.stexample.location.request.LocationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final ApiService apiService;

    /**
     * API request template을 통해 외부 API를 호출합니다.
     * - 요청 정보를 직접 입력하여 API를 호출합니다.
     * @param method HTTP Method
     * @param req 요청 본문
     * @param responseType 응답 본문 타입
     * @return API 응답
     * @param <T> 응답 타입
     */
    public <T> ApiResponse callApi(HttpMethod method, Object req, Class<T> responseType) {
        return apiService.execute(ApiRequestBuilder.<T>builder()
                .headers(apiService.createHeaders())
                .method(method)
                .url("https://api.smartthings.com/v1/locations")
                .body(Objects.requireNonNullElse(req, new Object()))
                .responseType(responseType)
                .build()
        );
    }
}
