package org.arpha.dto.order.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.CustomerDetails;
import org.arpha.dto.order.DeliveryDetails;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailsResponse {

    private DeliveryDetails deliveryDetails;
    private CustomerDetails customerDetails;
    private List<OrderItemResponse> orderedItems;

}
