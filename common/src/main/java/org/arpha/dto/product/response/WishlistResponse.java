package org.arpha.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponse {
    private long id;
    private String userEmail;
    private Set<ProductResponse> products;
    private String shareableLink;
}
