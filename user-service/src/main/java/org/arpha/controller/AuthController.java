package org.arpha.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.RefreshTokenRequest;
import org.arpha.dto.user.response.LoginResponse;
import org.arpha.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public LoginResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return authService.reissueAccessToken(request);
    }

}
