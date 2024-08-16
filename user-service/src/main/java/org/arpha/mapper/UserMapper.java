package org.arpha.mapper;

import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.entity.User;
import org.arpha.mapper.helper.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password", qualifiedByName="encodePassword")
    @Mapping(target = "subscribedToNewsLetter", source = "subscribedToNewsLetter")
    @Mapping(target = "active", constant = "false")
    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(CreateUserRequest createUserRequest);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    CreateUserResponse toCreateUserResponse(User user);

}
