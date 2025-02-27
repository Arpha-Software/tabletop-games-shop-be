package org.arpha.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.product.Dimension;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductTypeResponse {

    private Long id;
    private String name;
    private Dimension dimension;

}
