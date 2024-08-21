package com.clp.stexample.room.controller;

import com.clp.stexample.common.model.ApiResponse;
import com.clp.stexample.common.response.CommonResponse;
import com.clp.stexample.common.service.ApiService;
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

    private final ApiService apiService;

    @GetMapping("/locations/{locationId}/rooms")
    public ApiResponse<Object> getRoomList(@PathVariable String locationId) {
        return apiService.get(LIST_ROOMS, Object.class, locationId);
    }

    @GetMapping("/locations/{locationId}/rooms/{roomId}")
    public ApiResponse<Object> getRoomList(@PathVariable String locationId, @PathVariable String roomId) {
        return apiService.get(GET_A_ROOM, Object.class, locationId, roomId);
    }

    ///

    @GetMapping("/test4567/{locationId}")
    public CommonResponse test1234(@PathVariable String locationId) {
        return apiService.call(LIST_ROOMS, locationId);
    }

    @PostMapping("/test4567")
    public CommonResponse test123(@Valid @RequestBody LocationReq request) {
        return apiService.callWithRequest(CREATE_A_LOCATION, request, Object.class);
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
}
