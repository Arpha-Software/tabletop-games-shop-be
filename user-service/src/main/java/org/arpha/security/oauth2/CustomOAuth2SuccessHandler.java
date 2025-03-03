package org.arpha.security.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.TokenDetails;
import org.arpha.dto.user.response.LoginResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.security.jwt.JwtUtils;
import org.arpha.service.UserService;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.security.oauth2.feRedirectUrl}")
    private String frontendUrl;

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
        TokenDetails accessToken = jwtUtils.generateAccessToken(email);
        TokenDetails refreshToken = jwtUtils.generateRefreshToken(email);
        response.sendRedirect(getRedirectUrl(LoginResponse.of(userResponse, accessToken, refreshToken)));
    }

    private String getRedirectUrl(LoginResponse loginResponse) {
        return frontendUrl + "/callback?" +
                "accessToken=" + loginResponse.getAccessToken().getToken() +
                "&" +
                "accessTokenExpirationDate=" + loginResponse.getAccessToken().getExpires() +
                "&" +
                "refreshToken=" + loginResponse.getRefreshToken().getToken() +
                "&" +
                "refreshTokenExpirationDate=" + loginResponse.getRefreshToken().getExpires() +
                "&" +
                "email=" + loginResponse.getUserDetails().getEmail() +
                "&" +
                "firstName=" + loginResponse.getUserDetails().getFirstName() +
                "&" +
                "lastName=" + loginResponse.getUserDetails().getLastName() +
                "&" +
                "id=" + loginResponse.getUserDetails().getId() +
                "&" +
                "role=" + loginResponse.getUserDetails().getRole();
    }
}
