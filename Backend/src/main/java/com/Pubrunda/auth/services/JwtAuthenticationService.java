package com.Pubrunda.auth.services;

import com.Pubrunda.auth.dto.request.AuthenticationRequest;
import com.Pubrunda.auth.dto.request.RegisterUserRequest;
import com.Pubrunda.auth.dto.response.TokenAuthenticationResponse;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public TokenAuthenticationResponse register(RegisterUserRequest request) {
        User newUser = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), Role.USER);
        userRepository.save(newUser);

        String jwt = jwtService.generateToken(newUser);

        return new TokenAuthenticationResponse(jwt);
    }

    public TokenAuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String jwt = jwtService.generateToken(user);

        return new TokenAuthenticationResponse(jwt);
    }

}
