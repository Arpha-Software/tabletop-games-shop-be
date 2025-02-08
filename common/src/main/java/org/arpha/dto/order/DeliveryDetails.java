package org.arpha.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.enums.DeliveryType;
import org.arpha.dto.order.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryDetails {

    private DeliveryType deliveryType;
    private PaymentMethod paymentMethod;
    private String city;
    private String cityCode;
    private String street;
    private String streetCode;
    private String houseNumber;
    private String flatNumber;
    private String department;
    private String departmentCode;
    private String docNumber;
    private LocalDate expectedDeliveryDate;
    private BigDecimal deliveryPrice;

}
