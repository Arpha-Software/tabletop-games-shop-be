package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeAddressMethodProperties {

    @JsonProperty("CounterpartyRef")
    private String counterpartyRef;
    @JsonProperty("StreetRef")
    private String streetRef;
    @JsonProperty("BuildingNumber")
    private String buildingNumber;
    @JsonProperty("Flat")
    private String flat;
    @JsonProperty("Note")
    private String note;

    public CreateHomeAddressMethodProperties(String counterpartyRef, String streetRef, String buildingNumber, String flat) {
        this.counterpartyRef = counterpartyRef;
        this.streetRef = streetRef;
        this.buildingNumber = buildingNumber;
        this.flat = flat;
    }
}
