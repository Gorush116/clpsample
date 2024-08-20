package com.clp.stexample.common.service;

import com.clp.stexample.common.enums.ApiEndpoint;
import com.clp.stexample.common.error.ErrorResponse;
import com.clp.stexample.common.error.HTTPErrorCode;
import com.clp.stexample.common.model.ApiResponse;
import com.clp.stexample.common.response.ApiErrorResponse;
import com.clp.stexample.common.response.CommonResponse;
import com.clp.stexample.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;

    private final RestClient restClient;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Value("${smart-things.token}")
    private String token;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    public <T> ApiResponse<T> get(ApiEndpoint endpoint, Class<T> responseType, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
        return exchange(url, HttpMethod.GET, entity, responseType, uriVariables);
    }

    public <T> ApiResponse<T> post(ApiEndpoint endpoint, Object request, Class<T> responseType, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<Object> entity = new HttpEntity<>(request, createHeaders());
        return exchange(url, HttpMethod.POST, entity, responseType);
    }

    public <T> ApiResponse<T> put(ApiEndpoint endpoint, Object request, Class<T> responseType, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<Object> entity = new HttpEntity<>(request, createHeaders());
        return exchange(url, HttpMethod.PUT, entity, responseType);
    }

    public ApiResponse<Void> delete(ApiEndpoint endpoint, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
        return exchange(url, HttpMethod.DELETE, entity, Void.class, uriVariables);
    }

    public <T> ApiResponse<T> patch(ApiEndpoint endpoint, Object request, Class<T> responseType, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<Object> entity = new HttpEntity<>(request, createHeaders());
        return exchange(url, HttpMethod.PATCH, entity, responseType);
    }

    private <T> ApiResponse<T> exchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> responseType, Object... uriVariables) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType, uriVariables);
            return ApiResponse.success(response.getBody(), response.getStatusCode());
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return handleErrorResponse(ex);
        } catch (RestClientException ex) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred: " + ex.getMessage());
        }
    }

    // Enum과 Request의 pathParameter 매핑
    public String buildUrl(ApiEndpoint endpoint, Object... uriVariables) {
        String url = apiBaseUrl + endpoint.getUrl();

        for (Object uriVariable : uriVariables) {
            url = url.replaceFirst("\\{[^/]+\\}", uriVariable.toString());
        }

        return url;
    }

    // RestClient 시작

    public ApiResponse<?> callApi(ApiEndpoint endpoint, Object... uriVariables) {
        return callWithRequestBody(endpoint, null, uriVariables);
    }

    public ApiResponse<?> callWithRequestBody(ApiEndpoint endpoint, Object request, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<?> entity = new HttpEntity<>(request, createHeaders());
        return exchangeForMap(url, endpoint.getMethod(), entity, uriVariables);
    }

    private ApiResponse<Map<String, Object>> exchangeForMap(
                String url,
                HttpMethod method,
                HttpEntity<?> entity,
                Object... uriVariables
    ) {
        try {
            // HTTP METHOD & URI SET
            var request = restClient.method(method)
                    .uri(url, uriVariables)
                    .headers(headersSpec -> headersSpec.putAll(entity.getHeaders()));

            // Optional을 사용하여 body가 있을 때만 SET
            Optional.ofNullable(entity.getBody()).ifPresent(request::body);

            // SEND REQUEST & GET RESPONSE
            Map<String, Object> responseBody = request.retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            // RestClient는 상태 코드를 쉽게 가져오는 방법이 없으므로 기본적으로 200을 설정
            return ApiResponse.success(responseBody, HttpStatus.OK);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
//            return handleErrorResponse(ex);
            return handleSTErrorResponse(ex);
        } catch (RestClientException ex) { // TODO: 보완 필요
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred: " + ex.getMessage());
        }
    }

    private <T> ApiResponse<T> handleSTErrorResponse(HttpStatusCodeException ex) {
        return ApiResponse.error(ex.getStatusCode(), ex.getResponseBodyAs(ErrorResponse.class));
    }

    private <T> ApiResponse<T> handleErrorResponse(HttpStatusCodeException ex) {
        HTTPErrorCode errorCode = HTTPErrorCode.fromHttpStatus(ex.getStatusCode());
        return ApiResponse.error(HttpStatusCode.valueOf(errorCode.getStatusCode()), errorCode.getDescription());
    }
    // RestClient 끝




    public CommonResponse test(ApiEndpoint endpoint, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<?> entity = new HttpEntity<>(null, createHeaders());
        try {
            // HTTP METHOD & URI SET
            var request = restClient.method(endpoint.getMethod())
                    .uri(url, uriVariables)
                    .headers(headersSpec -> headersSpec.putAll(entity.getHeaders()));

            // Optional을 사용하여 body가 있을 때만 SET
            Optional.ofNullable(entity.getBody()).ifPresent(request::body);

            // SEND REQUEST & GET RESPONSE
            Map<String, Object> responseBody = request.retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            return SuccessResponse.of(responseBody);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return ApiErrorResponse.fromException(ex);
        }
    }
}
