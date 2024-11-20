package org.arpha.dto.order.novaposhta.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContrAgentData {

    @JsonProperty("Ref")
    private String ref;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("MiddleName")
    private String middleName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Counterparty")
    private String counterParty;
    @JsonProperty("OwnershipForm")
    private String ownershipForm;
    @JsonProperty("OwnershipFormDescription")
    private String ownershipFormDescription;
    @JsonProperty("EDRPOU")
    private String edrpou;
    @JsonProperty("CounterpartyType")
    private String counterpartyType;

}
