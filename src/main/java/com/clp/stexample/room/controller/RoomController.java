package com.clp.stexample.room.controller;

import com.clp.stexample.common.model.ApiResponse;
import com.clp.stexample.common.response.CommonResponse;
import com.clp.stexample.common.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test4567/{locationId}")
    public CommonResponse test1234(@PathVariable String locationId) {
        return apiService.test(LIST_ROOMS, locationId);
    }

}
