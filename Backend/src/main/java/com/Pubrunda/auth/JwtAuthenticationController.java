package com.Pubrunda.auth;

import com.Pubrunda.auth.dto.request.AuthenticationRequest;
import com.Pubrunda.auth.dto.request.RegisterUserRequest;
import com.Pubrunda.auth.dto.response.TokenAuthenticationResponse;
import com.Pubrunda.auth.services.JwtAuthenticationService;
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
