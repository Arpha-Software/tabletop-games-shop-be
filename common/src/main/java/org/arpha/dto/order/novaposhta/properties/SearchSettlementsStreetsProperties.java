package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsStreetsProperties {

    @JsonProperty("StreetName")
    private String streetName;
    @JsonProperty("SettlementRef")
    private String settlementRef;
    @JsonProperty("Limit")
    private String limit;

}
