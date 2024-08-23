package com.clp.stexample.location.controller;

import com.clp.stexample.common.request.ApiRequest;
import com.clp.stexample.common.response.ApiResponse;
import com.clp.stexample.common.service.ApiService;
import com.clp.stexample.location.request.LocationReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.clp.stexample.common.enums.locations.Locations.*;
import static com.clp.stexample.common.enums.locations.Locations.DELETE_A_LOCATION;
import static com.clp.stexample.common.enums.locations.Rooms.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Locations API", description = "SmartThings API 중 Locations에 대한 설명입니다. SmartThings에서 발급 받은 토큰이 필요합니다.")
public class LocationController {

    private final ApiService apiService;

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
}
