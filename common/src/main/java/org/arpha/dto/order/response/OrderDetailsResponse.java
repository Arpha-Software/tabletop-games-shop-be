package org.arpha.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.CustomerDetails;
import org.arpha.dto.order.DeliveryDetails;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsResponse {

    private DeliveryDetails deliveryDetails;
    private CustomerDetails customerDetails;
    private List<OrderItemResponse> orderedItems;

}
