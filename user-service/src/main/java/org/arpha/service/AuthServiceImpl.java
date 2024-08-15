package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.LoginRequest;
import org.arpha.dto.user.response.LoginResponse;
import org.arpha.mapper.AuthMapper;
import org.arpha.security.UserDetailsAdapter;
import org.arpha.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthMapper authMapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return Optional
                .of(loginRequest)
                .map(loginRequest1 -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest1.getUsername(), loginRequest1.getPassword())))
                .map(authentication -> (UserDetailsAdapter) authentication.getPrincipal())
                .map(UserDetailsAdapter::user)
                .map(user -> authMapper.toLoginResponse(user, jwtUtils.generateToken(user.getEmail())))
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
    }

}
