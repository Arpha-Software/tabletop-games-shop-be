package org.arpha.dto.product.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyProductTypeRequest {

    @NotEmpty(message = "Name of the product type can't be empty!")
    private String name;
    @DecimalMin(value = "0.1", message = "Width shouldn't be less than 0,1 cm")
    private BigDecimal width;
    @DecimalMin(value = "0.1", message = "Height shouldn't be less than 0,1 cm")
    private BigDecimal height;
    @DecimalMin(value = "0.001", message = "Weight shouldn't be less than 0,001 gram")
    private BigDecimal weight;
    @DecimalMin(value = "0.1", message = "Weight shouldn't be less than 0,1 cm")
    private BigDecimal length;


}
