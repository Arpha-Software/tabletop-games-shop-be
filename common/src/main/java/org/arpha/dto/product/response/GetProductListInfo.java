package org.arpha.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductListInfo {

    private long id;
    private long quantity;
    private String name;
    private String mainImgLink;
    private BigDecimal price;

}
