package com.clp.stexample.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;

@Controller
public class CommonController {
	
    @Operation(summary = "home", description = "Can be used as healyh check")
    @GetMapping("/home")
    @ResponseBody
    public String homeCheck() {
        return "Hello";
    }

    
    @Operation(summary = "login", description = "login page access")
    @GetMapping("/login")
    public String loginCheck() {
        return "Login";
    }
}
