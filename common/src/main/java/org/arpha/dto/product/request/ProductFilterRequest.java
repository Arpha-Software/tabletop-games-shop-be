package org.arpha.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterRequest {
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer minAge;
    private Set<String> categories;
    private Set<String> genres;
    private Set<String> mechanics;
    private String author;
    private String publisher;
}
