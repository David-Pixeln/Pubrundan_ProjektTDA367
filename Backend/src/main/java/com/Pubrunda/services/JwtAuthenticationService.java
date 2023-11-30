package com.Pubrunda.services;

import com.Pubrunda.dto.request.AuthenticationRequest;
import com.Pubrunda.dto.request.RegisterUserRequest;
import com.Pubrunda.dto.response.JwtAuthenticationResponse;
import com.Pubrunda.models.Role;
import com.Pubrunda.models.User;
import com.Pubrunda.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse register(RegisterUserRequest request) {
        User newUser = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), Role.USER);
        userRepository.save(newUser);

        String jwt = jwtService.generateToken(newUser);

        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

}
