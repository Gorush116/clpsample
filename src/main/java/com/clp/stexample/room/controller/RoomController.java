package com.clp.stexample.room.controller;

import com.clp.stexample.common.response.CommonResponse;
import com.clp.stexample.common.service.CommonApiService;
import com.clp.stexample.location.request.LocationReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.clp.stexample.common.enums.locations.Locations.*;
import static com.clp.stexample.common.enums.locations.Locations.DELETE_A_LOCATION;
import static com.clp.stexample.common.enums.locations.Rooms.*;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final CommonApiService apiService;

    @GetMapping("/test4567")
    public CommonResponse getLocationList() {
        return apiService.call(LIST_LOCATIONS);
    }

    @PostMapping("/test4567")
    public CommonResponse createLocation(@Valid @RequestBody LocationReq request) {
        return apiService.callWithRequest(CREATE_A_LOCATION, request);
    }

    @PutMapping("/test4567/{locationId}")
    public CommonResponse updateLocation(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.callWithRequest(UPDATE_A_LOCATION, request, locationId);
    }

    @PatchMapping("/test4567/{locationId}")
    public CommonResponse patchLocation(
            @Valid @RequestBody Map<String, Object> request,
            @PathVariable String locationId
    ) {
        return apiService.callWithRequest(PATCH_A_LOCATION, request, locationId);
    }

    @DeleteMapping("/test4567/{locationId}")
    public CommonResponse deleteLocation(@PathVariable String locationId) {
        return apiService.call(DELETE_A_LOCATION, locationId);
    }

    @GetMapping("/test4567/{locationId}")
    public CommonResponse getRoomList(@PathVariable String locationId) {
        return apiService.call(LIST_ROOMS, locationId);
    }

    //
    @GetMapping("/request")
    public CommonResponse getParam(@RequestParam Map<String, Object> request) {
        return apiService.callWithRequest(LIST_LOCATIONS, request);
    }
}
