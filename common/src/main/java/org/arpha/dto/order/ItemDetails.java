package org.arpha.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetails {

    private long id;
    private String name;
    private BigDecimal price;
    private long quantity;
    private String mainImg;

}
