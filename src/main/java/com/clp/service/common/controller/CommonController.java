<<<<<<< HEAD:src/main/java/com/clp/stexample/common/controller/CommonController.java
package com.clp.stexample.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clp.stexample.common.service.UserService;

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
    
    @Autowired
    private UserService userService;
    
    
    @Operation(summary = "test", description = "test")
    @GetMapping("/test/con")
    @ResponseBody
    public String testCon() {
    	userService.findAll();
        return "Hello";
    }
}
=======
package com.clp.service.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;

@Controller
public class CommonController {
	
    @Operation(summary = "home", description = "Can be used as health check")
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
>>>>>>> 9b40eebe3b009d1ed175cceb47eb4960cb90c96c:src/main/java/com/clp/service/common/controller/CommonController.java
