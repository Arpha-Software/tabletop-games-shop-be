package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchSettlementsStreetsProperties {

    @JsonProperty("StreetName")
    private String streetName;
    @JsonProperty("SettlementRef")
    private String settlementRef;
    @JsonProperty("Limit")
    private String limit;
}
