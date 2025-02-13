package org.arpha.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Delivery type can't null")
    private DeliveryType deliveryType;
    @NotNull(message = "Payment method can't null")
    private PaymentMethod paymentMethod;
    @NotEmpty(message = "City can't be null")
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
