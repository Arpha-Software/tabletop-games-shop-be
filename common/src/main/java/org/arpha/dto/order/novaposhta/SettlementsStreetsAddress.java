package org.arpha.dto.order.novaposhta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementsStreetsAddress {

    @JsonProperty("SettlementRef")
    private String settlementRef;
    @JsonProperty("SettlementStreetRef")
    private String settlementStreetRef;
    @JsonProperty("SettlementStreetDescription")
    private String settlementStreetDescription;
    @JsonProperty("Present")
    private String present;
    @JsonProperty("StreetsType")
    private String streetsType;
    @JsonProperty("StreetsTypeDescription")
    private String streetsTypeDescription;
    @JsonProperty("SettlementStreetDescriptionRu")
    private String settlementStreetDescriptionRu;
    @JsonProperty("Location")
    private Location location;

}
