package org.arpha.mapper;

import org.arpha.dto.order.novaposhta.data.CreateContrAgentData;
import org.arpha.dto.order.novaposhta.data.GetCounterpartiesData;
import org.arpha.dto.order.novaposhta.data.GetCounterpartyContactPersonsData;
import org.arpha.dto.order.novaposhta.data.SearchWarehousesData;
import org.arpha.dto.order.novaposhta.properties.CreateConsignmentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.arpha.dto.order.response.GetCounterpartiesResponse;
import org.arpha.dto.order.response.GetCounterpartyContactPersonsResponse;
import org.arpha.entity.Order;
import org.arpha.mapper.helper.ConsignmentDocumentMapperHelper;
import org.arpha.property.NovaPoshtaSenderProperties;
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
    @Mapping(target = "firstName", source = "senderFirstName")
    @Mapping(target = "middleName", source = "senderMiddleName")
    @Mapping(target = "lastName", source = "senderLastName")
    @Mapping(target = "phone", source = "senderPhone")
    @Mapping(target = "counterpartyType", constant = "PrivatePerson")
    @Mapping(target = "counterpartyProperty", constant = "Recipient")
    CreateContrAgentMethodProperties toCreateContrAgentMethodProperties(NovaPoshtaSenderProperties novaPoshtaSenderProperties);
    @Mapping(target = "cityName", source = "order.deliveryDetails.city")
    @Mapping(target = "findByString", source = "order.deliveryDetails.department")
    @Mapping(target = "limit", constant = "5")
    @Mapping(target = "page", constant = "1")
    SearchWarehouseMethodProperties toSearchWarehouseMethodProperties(Order order);
    @Mapping(target = "findByString", source = "departmentName")
    @Mapping(target = "limit", constant = "5")
    @Mapping(target = "page", constant = "1")
    SearchWarehouseMethodProperties toSearchWarehouseMethodProperties(NovaPoshtaSenderProperties novaPoshtaSenderProperties);

    @Mapping(target = "senderWarehouseIndex", source = "senderWarehouse.warehouseIndex")
    @Mapping(target = "recipientWarehouseIndex", source = "recipientWarehouse.warehouseIndex")
    @Mapping(target = "payerType", constant = "Recipient")
    @Mapping(target = "paymentMethod", source = "order.deliveryDetails", qualifiedByName = "toPaymentMethod")
    @Mapping(target = "dateTime", source = "order", qualifiedByName = "toDatetime")//????????????????????????????????
    @Mapping(target = "cargoType", constant = "Cargo")
    @Mapping(target = "serviceType", source = "order.deliveryDetails", qualifiedByName = "toServiceType")
    @Mapping(target = "seatsAmount", constant = "1")
    @Mapping(target = "cost", source = "order", qualifiedByName = "toCost")
    @Mapping(target = "citySender", source = "senderWarehouse.cityRef")
    @Mapping(target = "sender", source = "sender", qualifiedByName = "toSenderRef")
    @Mapping(target = "senderAddress", source = "senderWarehouse.ref")
    @Mapping(target = "contactSender", source = "contactSender", qualifiedByName = "toSenderContactRef")
    @Mapping(target = "sendersPhone", source = "novaPoshtaSenderProperties.senderPhone")
    @Mapping(target = "cityRecipient", source = "recipientWarehouse.cityRef")
    @Mapping(target = "recipient", source = "recipientAgent.ref")
    @Mapping(target = "recipientAddress", source = "recipientWarehouse.ref")
    @Mapping(target = "contactRecipient", source = "recipientAgent", qualifiedByName = "toRecipientContactRef")
    @Mapping(target = "recipientsPhone", source = "order.customerDetails.phoneNumber")
    @Mapping(target = "OptionsSeat", source = "order", qualifiedByName = "toOptionsSeat")
    @Mapping(target = "backwardDeliveryData", source = "order", qualifiedByName = "toBackwardDeliveryData")
    @Mapping(target = "description", constant = "Доставка з магазину настільних ігор")
    CreateConsignmentMethodProperties toCreateConsignmentMethodProperties(Order order, GetCounterpartiesResponse sender,
                                                                          GetCounterpartyContactPersonsResponse contactSender,
                                                                          CreateContrAgentData recipientAgent,
                                                                          SearchWarehousesData recipientWarehouse,
                                                                          SearchWarehousesData senderWarehouse);



}
