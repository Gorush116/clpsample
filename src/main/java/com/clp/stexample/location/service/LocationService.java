package com.clp.stexample.location.service;

import com.clp.stexample.common.request.ApiRequestBuilder;
import com.clp.stexample.common.response.ApiResponse;
import com.clp.stexample.common.service.ApiService;
import com.clp.stexample.common.utils.RequestUtils;
import com.clp.stexample.location.request.LocationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class LocationService {

    // Base Url 정보(자원정보 참조)
    @Value("${api.base.url}")
    private String apiBaseUrl;

    // 토큰값
    @Value("${smart-things.token}")
    private String token;

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
                .headers(RequestUtils.createHeaders(token))
                .method(method)
                .url(apiBaseUrl + "/locations")
                .body(Objects.requireNonNullElse(req, new Object()))
                .responseType(responseType)
                .build()
        );
    }

    public String callSyncApi() {
        // 1. 현재 스레드의 이름을 출력합니다.
        System.out.println("Execute method synchronously " + Thread.currentThread().getName());

        // 비동기 메서드 호출
        CompletableFuture<String> future = callAsyncApi();

        try {
            // 2. 스레드를 5초간 일시 중지 시킵니다.
            Thread.sleep(5000);
            
            // 비동기 메서드 실행 결과값을 기다려서 받습니다.
            String result = future.get();
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return "Error: " + e.getMessage();
        }

        // 3. 작업이 완료된 후 결과를 반환합니다.
        return "hello world !!!!";
    }

    /**
     * [Async] 반환 유형이 존재하는 경우 : CompletableFuture
     *
     * @return CompletableFuture<String>
     */
    @Async("taskExecutor")
    public CompletableFuture<String> callAsyncApi() {
        // 1. 현재 스레드의 이름을 출력합니다.
        System.out.println("Execute method asynchronously " + Thread.currentThread().getName());

        // 2. 비동기 작업을 수행할 CompletableFuture 객체를 생성합니다.
        return CompletableFuture.supplyAsync(() -> {
            try {
                // 3. 스레드를 5초간 일시 중지 시킵니다.
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Error: " + e.getMessage();
            }
            // 4. 비동기 결과가 성공하였을때 반환되는 값입니다.
            return "hello world !!!!";
        });
    }

    /**
     * [Async] 반환 유형이 존재하는 경우 콜백 처리 : CompletableFuture
     *
     * @return CompletableFuture<String>
     */
    public CompletableFuture<String> callAsyncCallback() {
        // 1. CompletableFuture 객체를 생성하고, 비동기 작업을 수행하여 결과값을 반환받습니다.
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        // 2. 비동기 작업이 완료되었을 때 수행할 동작을 정의합니다.
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                // 3. 비동기 작업이 성공적으로 완료되었을 때 실행되는 블록
                System.out.println("Completed successfully with result: " + result);
            } else {
                // 4. 비동기 작업이 실패했을 때 실행되는 블록
                System.out.println("Completed with error: " + exception.getMessage());
            }
        });

        // 5. 메인 스레드가 종료되지 않도록 2초간 대기합니다.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("error :: " + e.getMessage());
        }

        // 6. CompletableFuture 객체를 반환합니다.
        return future;
    }
}
