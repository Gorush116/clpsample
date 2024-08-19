package com.clp.stexample.device.controller;

import com.clp.stexample.common.model.ApiResponse;
import com.clp.stexample.common.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.clp.stexample.common.enums.devices.Devices.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class DeviceController {

    private final ApiService apiService;

    @GetMapping("/devices/{deviceId}")
    public ApiResponse<?> getDeviceDesc(@PathVariable String deviceId) {
        return apiService.callApi(GET_THE_DESCRIPTION_OF_A_DEVICE, Object.class, deviceId);
    }

}
