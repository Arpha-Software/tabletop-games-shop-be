package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SearchWarehouseMethodProperties {
    @JsonProperty("FindByString")
    private String findByString;
    @JsonProperty("CityName")
    private String cityName;
    @JsonProperty("CityRef")
    private String cityRef;
    @JsonProperty("Page")
    private String page;
    @JsonProperty("Limit")
    private String limit;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("TypeOfWarehouseRef")
    private String typeOfWarehouseRef;
    @JsonProperty("WarehouseId")
    private String warehouseId;
}
