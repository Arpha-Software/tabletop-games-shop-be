package org.arpha.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.response.LoginResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.mapper.AuthMapper;
import org.arpha.security.jwt.JwtUtils;
import org.arpha.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AuthMapper authMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String email = oauthToken.getPrincipal().getAttribute("email");
        UserResponse userResponse;

        if(!userService.existByEmail(email)) {
            String firstName = oauthToken.getPrincipal().getAttribute("given_name");
            String lastName = oauthToken.getPrincipal().getAttribute("family_name");
            userResponse = userService.createUser(email, firstName, lastName);
        } else {
            userResponse = userService.findUserByEmail(email);
        }
        String jwtToken = jwtUtils.generateToken(email);
        LoginResponse loginResponse =  authMapper.toLoginResponse(userResponse, jwtToken);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}
