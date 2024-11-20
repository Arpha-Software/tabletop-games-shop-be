package org.arpha.dto.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.FileAccessLink;
import org.arpha.dto.media.response.FileResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsResponse {

    private long id;
    private String name;
    private String type;
    private String genre;
    private Integer playerNumber;
    private Integer playTime;
    private String description;
    private BigDecimal price;
    private String rulesLink;
    private String createdBy;
    private String updatedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Set<String> categories;
    private Set<String> genres;
    private List<FileResponse> fileResponses;

}
