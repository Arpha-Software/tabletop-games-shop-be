package org.arpha.dto.product.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationDetails {
    private String author;
    private String publisher;
}
