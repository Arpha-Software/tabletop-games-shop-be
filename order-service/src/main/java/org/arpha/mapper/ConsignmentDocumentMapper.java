package org.arpha.mapper;

import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.SearchWarehousesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsignmentDocumentMapper {


    @Mapping(target = "firstName", source = "customerDetails.firstName")
    @Mapping(target = "middleName", source = "customerDetails.middleName")
    @Mapping(target = "lastName", source = "customerDetails.lastName")
    @Mapping(target = "phone", source = "customerDetails.phone")
    @Mapping(target = "email", source = "customerDetails.email")
    @Mapping(target = "counterpartyType", constant = "PrivatePerson")
    @Mapping(target = "counterpartyProperty", constant = "Recipient")
    CreateContrAgentMethodProperties toCreateContrAgentMethodProperties(CreateOrderRequest createOrderRequest);

    SearchWarehouseMethodProperties toSearchWarehouseMethodProperties(CreateOrderRequest createOrderRequest);

}
