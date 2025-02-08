package org.arpha.mapper;

import org.arpha.dto.order.novaposhta.SettlementsStreetsAddress;
import org.arpha.dto.order.novaposhta.data.CreateContrAgentData;
import org.arpha.dto.order.novaposhta.data.CreateHomeAddressData;
import org.arpha.dto.order.novaposhta.data.SearchWarehousesData;
import org.arpha.dto.order.novaposhta.properties.CreateConsignmentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.CreateHomeAddressMethodProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsStreetsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.arpha.dto.order.request.CreateConsignmentDocumentRequest;
import org.arpha.dto.order.response.GetCounterpartiesResponse;
import org.arpha.dto.order.response.GetCounterpartyContactPersonsResponse;
import org.arpha.entity.DeliveryAddress;
import org.arpha.entity.Order;
import org.arpha.mapper.helper.ConsignmentDocumentMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ConsignmentDocumentMapperHelper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsignmentDocumentMapper {

    @Mapping(target = "firstName", source = "customerDetails.firstName")
    @Mapping(target = "middleName", source = "customerDetails.middleName")
    @Mapping(target = "lastName", source = "customerDetails.lastName")
    @Mapping(target = "phone", source = "customerDetails.phoneNumber")
    @Mapping(target = "email", source = "customerDetails.email")
    @Mapping(target = "counterpartyType", constant = "PrivatePerson")
    @Mapping(target = "counterpartyProperty", constant = "Recipient")
    CreateContrAgentMethodProperties toCreateContrAgentMethodProperties(Order order);

    @Mapping(target = "cityName", source = "order.deliveryDetails.deliveryAddress.city")
    @Mapping(target = "findByString", source = "order.deliveryDetails.deliveryAddress.department")
    @Mapping(target = "limit", constant = "5")
    @Mapping(target = "page", constant = "1")
    SearchWarehouseMethodProperties toSearchWarehouseMethodProperties(Order order);

    @Mapping(target = "findByString", source = "senderDepartment")
    @Mapping(target = "cityName", source = "city")
    @Mapping(target = "limit", constant = "5")
    @Mapping(target = "page", constant = "1")
    SearchWarehouseMethodProperties toSearchWarehouseMethodProperties(CreateConsignmentDocumentRequest request);

    @Mapping(target = "payerType", constant = "Recipient")
    @Mapping(target = "recipientType", constant = "PrivatePerson")
    @Mapping(target = "paymentMethod", source = "order.deliveryDetails", qualifiedByName = "toPaymentMethod")
    @Mapping(target = "dateTime", source = "documentRequest.sendDate", qualifiedByName = "toDate")
    @Mapping(target = "cargoType", constant = "Cargo")
    @Mapping(target = "serviceType", source = "order.deliveryDetails", qualifiedByName = "toServiceType")
    @Mapping(target = "seatsAmount", source = "order", qualifiedByName = "toSeatsAmount")
    @Mapping(target = "cost", source = "order", qualifiedByName = "toCost")
    @Mapping(target = "citySender", source = "documentRequest.cityCode")
    @Mapping(target = "sender", source = "sender", qualifiedByName = "toSenderRef")
    @Mapping(target = "senderAddress", source = "documentRequest.senderDepartmentCode")
    @Mapping(target = "contactSender", source = "contactSender", qualifiedByName = "toSenderContactRef")
    @Mapping(target = "sendersPhone", source = "contactSender", qualifiedByName = "toSendersPhone")
    @Mapping(target = "cityRecipient", source = "order.deliveryDetails.deliveryAddress.cityCode")
    @Mapping(target = "recipient", source = "recipientAgent.ref")
    @Mapping(target = "recipientAddress", source = "order.deliveryDetails.deliveryAddress.departmentCode")
    @Mapping(target = "contactRecipient", source = "recipientAgent", qualifiedByName = "toRecipientContactRef")
    @Mapping(target = "recipientsPhone", source = "order.customerDetails.phoneNumber")
    @Mapping(target = "optionsSeat", source = "order", qualifiedByName = "toOptionsSeat")
    @Mapping(target = "backwardDeliveryData", source = "order", qualifiedByName = "toBackwardDeliveryData")
    @Mapping(target = "description", constant = "Доставка з магазину настільних ігор")
    CreateConsignmentMethodProperties toCreateConsignmentMethodProperties(Order order, GetCounterpartiesResponse sender,
                                                                          GetCounterpartyContactPersonsResponse contactSender,
                                                                          CreateContrAgentData recipientAgent,
                                                                          CreateConsignmentDocumentRequest documentRequest);

    @Mapping(target = "recipientType", constant = "PrivatePerson")
    @Mapping(target = "payerType", constant = "Recipient")
    @Mapping(target = "paymentMethod", source = "order.deliveryDetails", qualifiedByName = "toPaymentMethod")
    @Mapping(target = "dateTime", source = "documentRequest.sendDate", qualifiedByName = "toDate")
    @Mapping(target = "cargoType", constant = "Cargo")
    @Mapping(target = "serviceType", source = "order.deliveryDetails", qualifiedByName = "toServiceType")
    @Mapping(target = "seatsAmount", source = "order", qualifiedByName = "toSeatsAmount")
    @Mapping(target = "cost", source = "order", qualifiedByName = "toCost")
    @Mapping(target = "citySender", source = "documentRequest.cityCode")
    @Mapping(target = "sender", source = "sender", qualifiedByName = "toSenderRef")
    @Mapping(target = "senderAddress", source = "documentRequest.senderDepartmentCode")
    @Mapping(target = "contactSender", source = "contactSender", qualifiedByName = "toSenderContactRef")
    @Mapping(target = "sendersPhone", source = "contactSender", qualifiedByName = "toSendersPhone")
    @Mapping(target = "cityRecipient", source = "order.deliveryDetails.deliveryAddress.cityCode")
    @Mapping(target = "recipient", source = "recipientAgent.ref")
    @Mapping(target = "recipientAddress", source = "recipientHouse.ref")
    @Mapping(target = "contactRecipient", source = "recipientAgent", qualifiedByName = "toRecipientContactRef")
    @Mapping(target = "recipientsPhone", source = "order.customerDetails.phoneNumber")
    @Mapping(target = "optionsSeat", source = "order", qualifiedByName = "toOptionsSeat")
    @Mapping(target = "backwardDeliveryData", source = "order", qualifiedByName = "toBackwardDeliveryData")
    @Mapping(target = "description", constant = "Доставка з магазину настільних ігор")
    CreateConsignmentMethodProperties toCreateConsignmentMethodProperties(Order order, GetCounterpartiesResponse sender,
                                                                          GetCounterpartyContactPersonsResponse contactSender,
                                                                          CreateContrAgentData recipientAgent,
                                                                          CreateHomeAddressData recipientHouse,
                                                                          CreateConsignmentDocumentRequest documentRequest);

    @Mapping(target = "cityName", source = "city")
    @Mapping(target = "limit", constant = "20")
    @Mapping(target = "page", constant = "1")
    SearchSettlementsProperties toSearchSettlementsProperties(String city);

    @Mapping(target = "settlementRef", source = "settlementRef")
    @Mapping(target = "streetName", constant = "streetName")
    @Mapping(target = "limit", constant = "20")
    SearchSettlementsStreetsProperties toSearchSettlementStreetsProperties(String settlementRef, String streetName);

    @Mapping(target = "contactPersonRef", source = "contactPersonRef")
    @Mapping(target = "addressType", constant = "Doors")
    @Mapping(target = "settlementRef", source = "deliveryAddress.cityCode")
    @Mapping(target = "addressRef", source = "deliveryAddress.streetCode")
    @Mapping(target = "buildingNumber", source = "deliveryAddress.houseNumber")
    @Mapping(target = "flat", source = "deliveryAddress.flatNumber")
    CreateHomeAddressMethodProperties toCreateHomeAddressMethodProperties(String contactPersonRef,
                                                                          DeliveryAddress deliveryAddress);
}
