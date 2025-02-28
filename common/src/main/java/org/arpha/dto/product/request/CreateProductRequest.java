package org.arpha.dto.product.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.misc.MimeTypeDeserializer;
import org.springframework.util.MimeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Product name can't be empty!")
    private String name;
    @Min(value = 1, message = "Product type id can't be empty")
    private Long productTypeId;
    @NotNull(message = "Player number can't be null!")
    private Integer playerNumber;
    @NotNull(message = "Player quantity can't be null!")
    private Integer quantity;
    @NotNull(message = "Player time can't be null!")
    private Integer playTime;
    @NotBlank(message = "Product description can't be empty!")
    private String description;
    @NotNull(message = "Product price can't be empty!")
    private BigDecimal price;
    @NotNull(message = "Width can't be empty!")
    private BigDecimal width;
    @NotNull(message = "Length can't be empty!")
    private BigDecimal length;
    @NotNull(message = "Height can't be empty!")
    private BigDecimal height;
    @NotNull(message = "Weight can't be empty!")
    private BigDecimal weight;
    private String rulesLink;
    private Set<String> categories;
    private Set<String> genres;
    private List<ProductFileRequest> fileUploadRequests = new ArrayList<>();

    @Data
    public static class ProductFileRequest {

        @NotNull
        @Schema(type = "string", example = "application/json")
        @JsonDeserialize(using = MimeTypeDeserializer.class)
        private MimeType type;
        @Min(1)
        private long fileSize;

    }

}
