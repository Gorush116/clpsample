package com.clp.stexample.location.controller;

import com.clp.stexample.common.model.ApiResponse;
import com.clp.stexample.common.service.ApiService;
import com.clp.stexample.location.dto.LocationResponse;
import com.clp.stexample.location.request.LocationReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.clp.stexample.common.enums.locations.Locations.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Locations API", description = "SmartThings API 중 Locations에 대한 설명입니다. SmartThings에서 발급 받은 토큰이 필요합니다.")
public class LocationController {

    private final ApiService apiService;

    @Operation(summary = "장소 목록 조회", description = "장소 목록을 가져온다.")
    @GetMapping("/locations")
    public ApiResponse<LocationResponse> getLocationList() {
        return apiService.get(LIST_LOCATIONS, LocationResponse.class);
    }

    @Operation(summary = "장소 등록", description = "장소를 등록한다.")
    @PostMapping("/locations")
    public ApiResponse<Object> createLocation(@Valid @RequestBody LocationReq request) {
        return apiService.post(CREATE_A_LOCATION, request, Object.class);
    }

    @PutMapping("/locations/{locationId}")
    public ApiResponse<Object> updateLocation(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.put(UPDATE_A_LOCATION, request, Object.class, locationId);
    }

    @PatchMapping("/locations/{locationId}")
    public ApiResponse<Object> patchLocation(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.patch(PATCH_A_LOCATION, request, Object.class, locationId);
    }

    @DeleteMapping("/locations/{locationId}")
    public ApiResponse<Void> deleteLocation(@PathVariable String locationId) {
        return apiService.delete(DELETE_A_LOCATION, locationId);
    }

    @Operation(
            summary="API_DAS_00050(이상상태 알림)",
            description=
                    " HRM에서 기기가 고장/비정상/교체 등 이상상태를 탐지 시, API로 CLP에 이상발생한 기기정보 및 이상상태를 전송하고, CLP에서는 API로 운영자에게 알림을 전달한다.\n" +
                    " 1) HRM에서 시리얼넘버 수신 \n" +
                    " 2) 시리얼넘버로 ST 에서 디바이스 정보 조회 \n" +
                    " 3) 디바이스 정보 조회후 CS ticket 등록 \n" +
                    " 4) 알람 정보 리턴"
    )
    @Parameter(name="DVC_MFT_NO", description="디바이스제조번호(시리얼넘버)")
    @Parameter(name="DVC_ID", description="디바이스ID")
    @Parameter(name="DVC_CLS_CD", description="디바이스구분코드")
    @Parameter(name="DVC_NM", description="디바이스명")
    @Parameter(name="DVC_STS_VAL", description="디바이스상태값")
    @Parameter(name="DVC_INFM_SND_DTM", description="디바이스알림발송일시")
    @Parameter(name="DVC_MSG_CN", description="디바이스메시지내용")
    @GetMapping("/locations/{locationId}")
    public ApiResponse<Object> getLocation(@PathVariable String locationId) {
        return apiService.get(GET_A_LOCATION, Object.class, locationId);
    }

    // Test Start
    @GetMapping("/test1234")
    public ApiResponse<?> call() {
        return apiService.callApi(LIST_LOCATIONS);
    }

    @PostMapping("/test1234")
    public ApiResponse<?> callPost(@Valid @RequestBody LocationReq request) {
        return apiService.callWithRequestBody(CREATE_A_LOCATION, request);
    }

    @GetMapping("/test1234/{locationId}")
    public ApiResponse<?> testLocationId(@PathVariable String locationId) {
        return apiService.callApi(GET_A_LOCATION, locationId);
    }

    @PutMapping("/test1234/{locationId}")
    public ApiResponse<?> update(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.callWithRequestBody(UPDATE_A_LOCATION, request, locationId);
    }

    @PatchMapping("/test1234/{locationId}")
    public ApiResponse<?> patch(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.callWithRequestBody(PATCH_A_LOCATION, request, locationId);
    }

    @DeleteMapping("/test1234/{locationId}")
    public ApiResponse<?> delete(@PathVariable String locationId) {
        return apiService.callApi(DELETE_A_LOCATION, locationId);
    }
    // Test End

}
