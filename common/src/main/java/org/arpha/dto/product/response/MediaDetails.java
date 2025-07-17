package org.arpha.dto.product.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MediaDetails {
    private String mainImgLink;
    private List<String> photos;
    private String rulesLink;
}
