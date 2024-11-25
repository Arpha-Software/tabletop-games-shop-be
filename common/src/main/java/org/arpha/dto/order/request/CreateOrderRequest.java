package org.arpha.dto.order.request;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private List<CreateOrderItem> orderedItems;
}
