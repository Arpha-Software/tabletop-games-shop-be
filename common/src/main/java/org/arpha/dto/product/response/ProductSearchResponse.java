package org.arpha.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponse {
    private long id;
    private String name;
    private BigDecimal price;
    private String mainImgLink;
}