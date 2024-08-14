package org.arpha.service;

import org.arpha.dto.user.request.LoginRequest;
import org.arpha.dto.user.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

}
