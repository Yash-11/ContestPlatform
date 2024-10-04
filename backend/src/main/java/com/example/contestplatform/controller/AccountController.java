package com.example.contestplatform.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.contestplatform.dto.AuthenticationRequest;
import com.example.contestplatform.dto.ContestantDTO;
import com.example.contestplatform.dto.UserDTO;
import com.example.contestplatform.security.JwtUtil;
import com.example.contestplatform.service.ContestantService;
import com.example.contestplatform.service.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private ContestantService contestantService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.error("Incorrect username or password");
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String role = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .findFirst()
        .orElse("");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setJwt(jwt);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register_contestant")
    public ResponseEntity<String> registerPatientPost(@RequestBody ContestantDTO contestantDTO) {
        contestantService.registerContestant(contestantDTO);
        return ResponseEntity.ok("Contestant registered");
    }

}

