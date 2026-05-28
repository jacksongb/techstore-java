package com.techstore.controller;

import com.techstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String token = userService.login(body.get("username"), body.get("password"));
        return Map.of("token", token, "user", Map.of("username", body.get("username")));
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {
        String token = userService.register(body.get("username"), body.get("password"));
        return Map.of("token", token, "user", Map.of("username", body.get("username")));
    }
}
