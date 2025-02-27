package org.arpha.dto.order.novaposhta.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCounterpartiesData {

    @JsonProperty("Description")
    private String description;
    @JsonProperty("Ref")
    private String ref;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Counterparty")
    private String counterparty;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("MiddleName")
    private String middleName;
    @JsonProperty("OwnershipFormRef")
    private String ownershipFormRef;
    @JsonProperty("OwnershipFormDescription")
    private String ownershipFormDescription;
    @JsonProperty("EDRPOU")
    private String edrpou;
    @JsonProperty("CounterpartyType")
    private String counterpartyType;

}
