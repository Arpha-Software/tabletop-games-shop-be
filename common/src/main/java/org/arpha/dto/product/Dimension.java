package org.arpha.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dimension {

    private BigDecimal width;
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal height;

}
