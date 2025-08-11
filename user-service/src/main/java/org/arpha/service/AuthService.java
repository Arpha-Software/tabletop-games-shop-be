package org.arpha.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arpha.domain.type.notification.NotificationType;
import org.arpha.domain.type.notification.configuration.NotificationProperties;
import org.arpha.dto.user.TokenDetails;
import org.arpha.dto.user.request.LoginWithCodeRequest;
import org.arpha.dto.user.request.RefreshTokenRequest;
import org.arpha.dto.user.request.SendVerificationCodeRequest;
import org.arpha.dto.user.response.LoginResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.exception.InternalAuthorizationException;
import org.arpha.security.jwt.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final VerificationCodeService verificationCodeService;
    private final NotificationService notificationService;
    private final OrderService orderService;

    public void sendVerificationCode(SendVerificationCodeRequest request) {
        String code = verificationCodeService.generateAndStoreCode(request.getIdentifier());

        NotificationProperties notificationProperties;
        if (request.getIdentifier().contains("@")) {
            notificationProperties = NotificationProperties.emailNotification()
                    .notificationType(NotificationType.CREDENTIAL_VERIFICATION)
                    .recipientEmail(request.getIdentifier())
                    .subject("Your Verification Code")
                    .body("Your verification code is: " + code)
                    .build();
        } else {
            notificationProperties = NotificationProperties.smsNotification()
                    .notificationType(NotificationType.CREDENTIAL_VERIFICATION)
                    .recipientPhoneNumber(request.getIdentifier())
                    .message("Your verification code is: " + code)
                    .build();
        }
        notificationService.triggerNotification(notificationProperties);
    }

    @Transactional
    public LoginResponse loginWithCode(LoginWithCodeRequest request) {
        String cachedCode = verificationCodeService.getCode(request.getIdentifier())
                .orElseThrow(() -> new InternalAuthorizationException("Invalid or expired verification code."));

        if (!cachedCode.equals(request.getCode())) {
            throw new InternalAuthorizationException("Invalid or expired verification code.");
        }
        verificationCodeService.evictCode(request.getIdentifier());

        UserResponse userResponse;
        if (userService.existByEmail(request.getIdentifier()) || userService.existByPhone(request.getIdentifier())) {
            userResponse = userService.findUserByIdentifier(request.getIdentifier());
        } else {
            String firstName = request.getFirstName() != null ? request.getFirstName() : "New";
            String lastName = request.getLastName() != null ? request.getLastName() : "User";
            userResponse = userService.createUser(request.getIdentifier(), firstName, lastName);
            List<Long> guestOrderIds = orderService.findGuestOrderIds(userResponse);

            if (!CollectionUtils.isEmpty(guestOrderIds)) {
                orderService.assignOrdersToUser(guestOrderIds, userResponse.getId());
            }
        }

        return LoginResponse.of(userResponse, jwtUtils.generateAccessToken(userResponse.getEmail() == null ? userResponse.getPhone() : userResponse.getEmail()),
                jwtUtils.generateRefreshToken(userResponse.getEmail() == null ? userResponse.getPhone() : userResponse.getEmail()));
    }


    public LoginResponse reissueAccessToken(RefreshTokenRequest request) {
        if (!jwtUtils.isTokenValid(request.getRefreshToken())) {
            throw new InternalAuthorizationException("Token is not valid or expired!");
        }

        String email = jwtUtils.getSubject(request.getRefreshToken());
        return LoginResponse.builder()
                .accessToken(jwtUtils.generateAccessToken(email))
                .refreshToken(jwtUtils.generateRefreshToken(email))
                .build();
    }

}
