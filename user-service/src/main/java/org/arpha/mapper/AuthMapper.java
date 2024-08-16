package org.arpha.mapper;

import org.arpha.dto.user.response.LoginResponse;
import org.arpha.entity.User;
import org.arpha.mapper.helper.AuthMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthMapperHelper.class})
public interface AuthMapper {

    @Mapping(target = "tokenDetails.token", source = "jwtToken")
    @Mapping(target = "tokenDetails.expires", source = "jwtToken", qualifiedByName = "toExpirationDate")
    @Mapping(target = "userDetails.id", source = "user.id")
    @Mapping(target = "userDetails.firstName", source = "user.firstName")
    @Mapping(target = "userDetails.lastName", source = "user.lastName")
    @Mapping(target = "userDetails.email", source = "user.email")
    LoginResponse toLoginResponse(User user, String jwtToken);

}
