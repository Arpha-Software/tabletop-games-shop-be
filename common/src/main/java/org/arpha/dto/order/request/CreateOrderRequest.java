package org.arpha.dto.order.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Customer details can't be null!")
    private CustomerDetails customerDetails;
    @NotNull(message = "Delivery details can't be null!")
    private DeliveryDetails deliveryDetails;
    @NotEmpty(message = "Order Items can't be empty or null!")
    private List<CreateOrderItem> orderedItems;
}
