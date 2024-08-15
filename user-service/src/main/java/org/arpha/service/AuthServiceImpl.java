package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.LoginRequest;
import org.arpha.dto.user.response.LoginResponse;
import org.arpha.security.UserDetailsAdapter;
import org.arpha.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetailsAdapter user = (UserDetailsAdapter) authenticated.getPrincipal();
        String jwtToken = jwtUtils.generateToken(user.getUsername());
        return new LoginResponse(user.getUsername(), jwtToken);

    }
}
