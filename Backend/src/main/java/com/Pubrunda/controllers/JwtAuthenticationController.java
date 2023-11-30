package com.Pubrunda.controllers;

import com.Pubrunda.dto.request.AuthenticationRequest;
import com.Pubrunda.dto.request.RegisterUserRequest;
import com.Pubrunda.dto.response.TokenAuthenticationResponse;
import com.Pubrunda.services.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("${api.baseurl}/auth")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final JwtAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<TokenAuthenticationResponse> register(@RequestBody RegisterUserRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenAuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
