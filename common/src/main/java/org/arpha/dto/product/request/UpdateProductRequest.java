package org.arpha.dto.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

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
    @NotNull(message = "Width price can't be empty!")
    private BigDecimal width;
    @NotNull(message = "Length price can't be empty!")
    private BigDecimal length;
    @NotNull(message = "Height price can't be empty!")
    private BigDecimal height;
    @NotNull(message = "Weight price can't be empty!")
    private BigDecimal weight;
    @NotBlank(message = "Product rules link can't be empty!")
    private String rulesLink;
    private Set<String> categories;
    private Set<String> genres;

}
