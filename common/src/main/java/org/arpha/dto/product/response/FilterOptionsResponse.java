package org.arpha.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptionsResponse {
    private Set<String> languages;
    private Set<String> genres;
    private Set<String> categories;
    private Set<String> mechanics;
    private Set<String> publishers;
    private Set<String> authors;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer minAge;
    private PriceRange priceRange;
}