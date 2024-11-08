package org.arpha.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.enums.DeliveryType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetails {

    private DeliveryType deliveryType;
    private String city;
    private String street;
    private String houseNumber;
    private String flatNumber;

}
