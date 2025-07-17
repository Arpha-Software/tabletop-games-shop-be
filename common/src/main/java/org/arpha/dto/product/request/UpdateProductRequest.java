package org.arpha.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private String name;
    private Long productTypeId;
    private String description;
    private BigDecimal price;
    private Integer quantity;

    // --- New Optional Fields for Update ---
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

    // --- Existing Optional Fields ---
    private String rulesLink;
    private BigDecimal width;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal weight;
    private Set<String> categories;
    private Set<String> genres;
}