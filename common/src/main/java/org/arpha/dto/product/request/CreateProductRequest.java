package org.arpha.dto.product.request;

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

}
