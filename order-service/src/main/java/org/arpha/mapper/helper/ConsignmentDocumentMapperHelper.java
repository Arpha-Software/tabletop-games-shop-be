package org.arpha.mapper.helper;

import org.arpha.dto.order.novaposhta.data.BackwardDeliveryData;
import org.arpha.dto.order.novaposhta.data.CreateContrAgentData;
import org.arpha.dto.order.novaposhta.data.OptionsSeatData;
import org.arpha.dto.order.response.GetCounterpartiesResponse;
import org.arpha.entity.DeliveryDetails;
import org.arpha.entity.Order;
import org.arpha.entity.OrderItem;
import org.arpha.entity.Product;
import org.arpha.exception.CreateContrAgentException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

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

    @Named("toRecipientContactRef")
    public String toRecipientContactRef(CreateContrAgentData createContrAgentData) {
        if (createContrAgentData.getContactPerson().getData().size() != 1) {
            throw new CreateContrAgentException("RecipientContactRef is 0");
        }
        return createContrAgentData.getContactPerson().getData().getFirst().getRef();
    }


    @Named("toSenderContactRef")
    public String toSenderContactRef(GetCounterpartiesResponse getCounterpartiesResponse) {
        if (getCounterpartiesResponse.getData().isEmpty()) {
            throw new CreateContrAgentException("SenderContactRef is 0");
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

    private Stream<OptionsSeatData> createOptionsSeat(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        return Stream.generate(() -> new OptionsSeatData(product.getWeight().toPlainString(), product.getLength().toPlainString(),
                product.getWidth().toPlainString(), product.getHeight().toPlainString())).limit(orderItem.getQuantity());
    }

}
