package org.arpha.dto.order.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.enums.OrderStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailsResponse {

    private long id;
    private OrderStatus orderStatus;
    private OffsetDateTime createdAt;
    private String city;
    private String street;
    private String houseNumber;
    private String flatNumber;
    private String department;
    private LocalDate expectedDeliveryDate;

}
