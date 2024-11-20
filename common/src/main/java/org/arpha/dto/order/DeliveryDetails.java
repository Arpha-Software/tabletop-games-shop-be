package org.arpha.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.enums.DeliveryType;
import org.arpha.dto.order.enums.PaymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetails {

    private DeliveryType deliveryType;
    private PaymentMethod paymentMethod;
    private String city;
    private String street;
    private String houseNumber;
    private String flatNumber;
    private String department;
}
