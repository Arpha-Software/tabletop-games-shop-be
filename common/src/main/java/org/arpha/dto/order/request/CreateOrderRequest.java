package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.DeliveryDetails;
import org.arpha.dto.order.CustomerDetails;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private CustomerDetails customerDetails;
    private DeliveryDetails deliveryDetails;
    private List<CreateOrderItem> orderItems;
}
