package org.edu.main.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.main.dto.request.user.UserRequest;
import org.edu.main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    private ResponseEntity<?> PROFILE(HttpServletRequest request) {
        return userService.PROFILE(request);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<?> UPDATE(@PathVariable long id, @Valid @RequestBody UserRequest request) {
        return userService.UPDATE(id, request);
    }

}
