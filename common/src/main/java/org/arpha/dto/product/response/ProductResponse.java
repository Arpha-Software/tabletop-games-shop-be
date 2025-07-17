package org.arpha.dto.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private long quantity;
    private GameDetails gameDetails;
    private ClassificationDetails classification;
    private PublicationDetails publicationDetails;
    private MediaDetails media;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
