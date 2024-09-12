package org.arpha.mapper;

import org.arpha.dto.user.response.LoginResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.mapper.helper.AuthMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthMapperHelper.class})
public interface AuthMapper {

    @Mapping(target = "tokenDetails.token", source = "jwtToken")
    @Mapping(target = "tokenDetails.expires", source = "jwtToken", qualifiedByName = "toExpirationDate")
    @Mapping(target = "userDetails.firstName", source = "userResponse.firstName")
    @Mapping(target = "userDetails.lastName", source = "userResponse.lastName")
    @Mapping(target = "userDetails.email", source = "userResponse.email")
    @Mapping(target = "userDetails.id", source = "userResponse.id")
    LoginResponse toLoginResponse(UserResponse userResponse, String jwtToken);

}
