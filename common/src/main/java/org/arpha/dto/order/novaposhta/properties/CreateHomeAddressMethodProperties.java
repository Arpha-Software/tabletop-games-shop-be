package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeAddressMethodProperties {

    @JsonProperty("ContactPersonRef")
    private String contactPersonRef;
    @JsonProperty("SettlementRef")
    private String settlementRef;
    @JsonProperty("AddressRef")
    private String addressRef;
    @JsonProperty("AddressType")
    private String addressType;
    @JsonProperty("BuildingNumber")
    private String buildingNumber;
    @JsonProperty("Flat")
    private String flat;
    @JsonProperty("Flat")
    private String note;

    public CreateHomeAddressMethodProperties(String contactPersonRef, String settlementRef, String addressRef, String buildingNumber, String flat) {
        this.contactPersonRef = contactPersonRef;
        this.settlementRef = settlementRef;
        this.addressRef = addressRef;
        this.addressType = "Doors";
        this.buildingNumber = buildingNumber;
        this.flat = flat;
    }
}
