package com.clp.service.location.controller;

import com.clp.service.common.response.ApiResponse;
import com.clp.service.common.service.ApiService;
import com.clp.service.location.request.LocationReq;
import com.clp.service.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.Future;

import static com.clp.service.common.enums.locations.Locations.*;
import static com.clp.service.common.enums.locations.Locations.DELETE_A_LOCATION;
import static com.clp.service.common.enums.locations.Rooms.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Locations API", description = "SmartThings API 중 Locations에 대한 설명입니다. SmartThings에서 발급 받은 토큰이 필요합니다.")
public class LocationController {

    private final ApiService apiService;

    private final LocationService locationService;

    @Operation(summary = "장소 목록 조회", description = "장소 목록을 가져온다.")
    @GetMapping("/locations")
    public ApiResponse getLocationList() {
        return apiService.call(LIST_LOCATIONS);
    }

    @PostMapping("/locations")
    public ApiResponse createLocation(@Valid @RequestBody LocationReq request) {
        return apiService.callWithRequest(CREATE_A_LOCATION, request);
    }

    @PutMapping("/locations/{locationId}")
    public ApiResponse updateLocation(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.callWithRequest(UPDATE_A_LOCATION, request, locationId);
    }

    @PatchMapping("/locations/{locationId}")
    public ApiResponse patchLocation(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.callWithRequest(PATCH_A_LOCATION, request, locationId);
    }

    @DeleteMapping("/locations/{locationId}")
    public ApiResponse deleteLocation(@PathVariable String locationId) {
        return apiService.call(DELETE_A_LOCATION, locationId);
    }

    @GetMapping("/locations/{locationId}")
    public ApiResponse getRoomList(@PathVariable String locationId) {
        return apiService.call(LIST_ROOMS, locationId);
    }

    @GetMapping("/locations/{locationId}/rooms/{roomId}")
    public ApiResponse getRoom(@PathVariable String locationId, @PathVariable String roomId, @RequestBody Map<String, Object> request) {
        return apiService.callWithRequest(LIST_ROOMS, request, locationId, roomId);
    }

    // API REQUEST TEMPLATE START
    @GetMapping("/call/api")
    public ApiResponse get(@RequestBody Map<String, Object> request) {
        return locationService.callApi(HttpMethod.GET, request, Object.class);
    }

    @PostMapping("/call/api")
    public ApiResponse post(@RequestBody LocationReq request) {
        return locationService.callApi(HttpMethod.POST, request, Object.class);
    }

    @PutMapping("/call/api")
    public ApiResponse put(@RequestBody Map<String, Object> request) {
        return locationService.callApi(HttpMethod.PUT, request, Object.class);
    }

    @PatchMapping("/call/api")
    public ApiResponse patch(@RequestBody Map<String, Object> request) {
        return locationService.callApi(HttpMethod.PATCH, request, Object.class);
    }

    @DeleteMapping("/call/api")
    public ApiResponse delete(@RequestBody Map<String, Object> request) {
        return locationService.callApi(HttpMethod.DELETE, request, Object.class);
    }
    // API REQUEST TEMPLATE END


    // ASYNC REQUEST START
    @GetMapping("/call/api/sync")
    public String syncGet(@RequestBody Map<String, Object> request) {
        return locationService.callSyncApi();
    }

    @GetMapping("/call/api/async")
    public Future<String> asyncGet(@RequestBody Map<String, Object> request) {
        return locationService.callAsyncApi();
    }
    // ASYNC REQUEST END

}
