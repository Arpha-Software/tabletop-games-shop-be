package org.arpha.aop;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.audit.TargetType;
import org.arpha.dto.user.response.ChangePasswordResponse;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.service.AuditService;
import org.arpha.utills.AspectUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static org.arpha.dto.audit.Action.*;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAuditAspect {

    private final AuditService auditService;

    @AfterReturning(
            value = "execution(public org.arpha.dto.user.response.CreateUserResponse createUser(org.arpha.dto.user.request.CreateUserRequest))",
            argNames = "createUserResponse",
            returning = "createUserResponse")
    public void createUserAdvice(CreateUserResponse createUserResponse) {
        auditService.saveAudit(CREATE_USER, createUserResponse.getId(), createUserResponse.getId(), TargetType.USER);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.user.response.ChangePasswordResponse changePassword(org.arpha.dto.user.request.ChangePasswordRequest))",
            argNames = "changePasswordResponse",
            returning = "changePasswordResponse")
    public void changeUserPasswordAdvice(ChangePasswordResponse changePasswordResponse) {
        auditService.saveAudit(CHANGE_USER_PASSWORD, changePasswordResponse.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.USER);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.user.response.UserResponse updateUser(long, org.arpha.dto.user.request.UpdateUserRequest))",
            argNames = "userResponse",
            returning = "userResponse")
    public void updateUserAdvice(UserResponse userResponse) {
        auditService.saveAudit(UPDATE_USER, userResponse.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.USER);
    }

    @AfterReturning(
            value = "execution(public void deleteUserById(long)) && args(id)",
            argNames = "id")
    public void deleteUserByIdAdvice(long id) {
        auditService.saveAudit(DELETE_USER_BY_ID, id, AspectUtils.getAuthenticatedUserId(), TargetType.USER);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.user.response.UserResponse findUserById(long)) && args(id)",
            argNames = "id")
    public void findUserByIdAdvice(long id) {
        auditService.saveAudit(FIND_USER_BY_ID, id, AspectUtils.getAuthenticatedUserId(), TargetType.USER);
    }

    @AfterReturning(
            value = "execution(public void activateAccount(long)) && args(id)",
            argNames = "id")
    public void activateUserByIdAdvice(long id) {
        auditService.saveAudit(ACTIVATE_ACCOUNT, id, AspectUtils.getAuthenticatedUserId(), TargetType.USER);

    }

}
