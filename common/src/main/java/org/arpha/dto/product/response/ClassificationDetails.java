package org.arpha.dto.product.response;


import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class ClassificationDetails {
    private String language;
    private Set<String> genres;
    private Set<String> categories;
    private Set<String> mechanics;
}
