package com.loginservice.login_service.controller;

import com.loginservice.login_service.dto.AuthDTO;
import com.loginservice.login_service.service.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody AuthDTO authDTO) throws NoSuchElementException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.login(authDTO));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
