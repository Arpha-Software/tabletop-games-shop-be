package org.arpha.notificationservice.mapper;

import org.arpha.notificationservice.common.SmsSendResult;
import org.arpha.notificationservice.dto.SmsServiceResponse;
import org.arpha.notificationservice.mapper.helper.SmsClientMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = SmsClientMapperHelper.class
)
public interface SmsClientMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "toSmsSendStatus")
    SmsSendResult toSmsSendResult(SmsServiceResponse response);

}
