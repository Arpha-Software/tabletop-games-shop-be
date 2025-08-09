package org.arpha.mapper;

import org.arpha.dto.user.request.CreateUserDeliveryAddressRequest;
import org.arpha.dto.user.response.UserDeliveryAddressResponse;
import org.arpha.entity.UserDeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDeliveryAddressMapper {

    UserDeliveryAddressResponse toUserDeliveryAddressResponse(UserDeliveryAddress address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "default", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDeliveryAddress toUserDeliveryAddress(CreateUserDeliveryAddressRequest request);
}
