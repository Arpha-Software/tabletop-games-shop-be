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

    // --- Core Required Fields ---
    @NotBlank(message = "Product name can't be empty!")
    private String name;

    @Min(value = 1, message = "Product type id can't be empty")
    private Long productTypeId;

    @NotBlank(message = "Product description can't be empty!")
    private String description;

    @NotNull(message = "Product price can't be empty!")
    private BigDecimal price;

    @NotNull(message = "Quantity can't be null!")
    private Integer quantity;

    // --- New Optional Fields ---
    @Schema(description = "Minimum number of players.")
    private Integer minPlayerNumber;

    @Schema(description = "Maximum number of players.")
    private Integer maxPlayerNumber;

    @Schema(description = "Minimum play time in minutes.")
    private Integer minPlayTime;

    @Schema(description = "Maximum play time in minutes.")
    private Integer maxPlayTime;

    @Schema(description = "Minimum recommended age.")
    private Integer minAge;

    @Schema(description = "Language of the game.")
    private String language;

    @Schema(description = "Publisher of the game.")
    private String publisher;

    @Schema(description = "Author or designer of the game.")
    private String author;

    @Schema(description = "Rating from BoardGameGeek (BGG).")
    private Double bggRating;

    @Schema(description = "Game complexity rating.")
    private Double complexity;

    @Schema(description = "A description of the game's components.")
    private String components;

    @Schema(description = "Set of game mechanics.")
    private Set<String> mechanics;

    // --- Existing Optional and Required Fields ---
    @Schema(description = "Link to the game's rules.")
    private String rulesLink;

    @Schema(description = "Product dimensions.")
    private BigDecimal width;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal weight;

    @Schema(description = "Set of categories for the product.")
    private Set<String> categories;

    @Schema(description = "Set of genres for the product.")
    private Set<String> genres;

    @Schema(description = "List of files to be uploaded for the product.")
    private List<ProductFileRequest> fileUploadRequests = new ArrayList<>();

    @Data
    public static class ProductFileRequest {
        @NotNull
        @Schema(type = "string", example = "application/json")
        @JsonDeserialize(using = MimeTypeDeserializer.class)
        private MimeType type;

        @Min(1)
        private long fileSize;
        private String uuid;
        private Boolean isMain;
    }
}
