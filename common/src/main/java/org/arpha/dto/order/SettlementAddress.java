package org.arpha.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementAddress {

    @JsonProperty("Warehouses")
    private String warehouses;
    @JsonProperty("MainDescription")
    private String mainDescription;
    @JsonProperty("Area")
    private String area;
    @JsonProperty("Region")
    private String region;
    @JsonProperty("SettlementTypeCode")
    private String settlementTypeCode;
    @JsonProperty("Ref")
    private String ref;
    @JsonProperty("Warehouses")
    private String deliveryCity;
}
