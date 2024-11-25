package org.arpha.dto.order.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.CustomerDetails;
import org.arpha.dto.order.DeliveryDetails;
import org.arpha.dto.order.ItemDetails;
import org.arpha.dto.order.enums.OrderStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderInfoResponse {

    private long id;
    private DeliveryDetails deliveryDetails;
    private CustomerDetails customerDetails;
    private OrderStatus orderStatus;
    private List<ItemDetails> orderedItems;
    private OffsetDateTime createdAt;

}
