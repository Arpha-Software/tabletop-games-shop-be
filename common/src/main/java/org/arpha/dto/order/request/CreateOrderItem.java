package org.arpha.dto.order.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItem {

    @Min(1)
    private long productId;
    @Min(1)
    private long quantity;

}
