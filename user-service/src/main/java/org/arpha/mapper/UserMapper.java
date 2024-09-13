package org.arpha.mapper;

import org.arpha.dto.user.request.UpdateUserRequest;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "role", source = "user.role")
    UserResponse toUserResponse(User user);

    @Mapping(target = "user.firstName", source = "updateUserRequest.firstName")
    @Mapping(target = "user.lastName", source = "updateUserRequest.lastName")
    @Mapping(target = "user.phone", source = "updateUserRequest.phone")
    @Mapping(target = "user.subscribedToNewsLetter", source = "updateUserRequest.subscribedToNewsLetter")
    User updateUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone",  ignore = true)
    @Mapping(target = "subscribedToNewsLetter", constant = "false")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(String firstName, String lastName, String email);

}
