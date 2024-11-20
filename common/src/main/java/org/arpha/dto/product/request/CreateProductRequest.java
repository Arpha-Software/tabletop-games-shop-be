package org.arpha.dto.product.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.serializer.MimeTypeDeserializer;
import org.springframework.util.MimeType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Product name can't be empty!")
    private String name;
    @NotBlank(message = "Product type can't be empty!")
    private String type;
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
    @NotBlank(message = "Product rules link can't be empty!")
    private String rulesLink;
    private Set<String> categories;
    private Set<String> genres;
    private List<ProductFileRequest> fileUploadRequests;


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
