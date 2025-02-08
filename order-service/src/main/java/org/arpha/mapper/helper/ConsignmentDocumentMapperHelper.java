package org.arpha.mapper.helper;

import org.arpha.dto.order.novaposhta.data.BackwardDeliveryData;
import org.arpha.dto.order.novaposhta.data.CreateContrAgentData;
import org.arpha.dto.order.novaposhta.data.OptionsSeatData;
import org.arpha.dto.order.response.GetCounterpartiesResponse;
import org.arpha.dto.order.response.GetCounterpartyContactPersonsResponse;
import org.arpha.entity.DeliveryDetails;
import org.arpha.entity.Order;
import org.arpha.entity.OrderItem;
import org.arpha.entity.Product;
import org.arpha.exception.CreateConsignmentDocumentException;
import org.arpha.exception.CreateOrderException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ConsignmentDocumentMapperHelper {

    @Named("toPaymentMethod")
    public String toPaymentMethod(DeliveryDetails deliveryDetails) {
        return deliveryDetails.getPaymentMethod().getValue();
    }

    @Named("toServiceType")
    public String toServiceType(DeliveryDetails deliveryDetails) {
        return deliveryDetails.getDeliveryType().getServiceType();
    }

    @Named("toCost")
    public String toCost(Order order) {
        return order.getTotalCost().toPlainString();
    }

    @Named("toSeatsAmount")
    public String toSeatsAmount(Order order) {
        return order.getOrderedItems().stream().map(OrderItem::getQuantity).reduce(0, Integer::sum).toString();
    }

    @Named("toRecipientContactRef")
    public String toRecipientContactRef(CreateContrAgentData createContrAgentData) {
        if (createContrAgentData.getContactPerson().getData().isEmpty()) {
            throw new CreateConsignmentDocumentException("RecipientContactRef is not found because response is empty");
        }
        return createContrAgentData.getContactPerson().getData().getFirst().getRef();
    }

    @Named("toSenderContactRef")
    public String toSenderContactRef(GetCounterpartiesResponse getCounterpartiesResponse) {
        if (getCounterpartiesResponse.getData().isEmpty()) {
            throw new CreateOrderException("SenderContactRef wasn't found");
        }
        return getCounterpartiesResponse.getData().getFirst().getRef();
    }

    @Named("toBackwardDeliveryData")
    public List<BackwardDeliveryData> toBackwardDeliveryData(Order order) {
        return List.of(new BackwardDeliveryData(order.getTotalCost().toPlainString()));
    }

    @Named("toOptionsSeat")
    public List<OptionsSeatData> toOptionsSeatData(Order order) {
        return order.getOrderedItems().stream().flatMap(this::createOptionsSeat).toList();
    }

    @Named("toSendersPhone")
    public String toSendersPhone(GetCounterpartyContactPersonsResponse getCounterpartyContactPersonsResponse) {
        if (getCounterpartyContactPersonsResponse.getData().isEmpty()) {
            throw new CreateConsignmentDocumentException("Can't find sender phone because response is empty");
        }
        return getCounterpartyContactPersonsResponse.getData().getFirst().getPhones();
    }

    @Named("toDate")
    public String toDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Named("toSenderRef")
    public String toSenderRef(GetCounterpartiesResponse getCounterpartiesResponse) {
        if (getCounterpartiesResponse.getData().isEmpty()) {
            throw new CreateConsignmentDocumentException("Can't find sender reference because response is empty");
        }
        return getCounterpartiesResponse.getData().getFirst().getRef();
    }

    @Named("toSenderContactRef")
    public String toSenderContactRef(GetCounterpartyContactPersonsResponse contactSender) {
        if (contactSender.getData().isEmpty()) {
            throw new CreateConsignmentDocumentException("Can't find contact sender reference because response is empty");
        }
        return contactSender.getData().getFirst().getRef();
    }

    private Stream<OptionsSeatData> createOptionsSeat(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        return Stream.generate(() -> new OptionsSeatData(product.getWeight().toPlainString(), product.getLength().toPlainString(),
                product.getWidth().toPlainString(), product.getHeight().toPlainString())).limit(orderItem.getQuantity());
    }

}
