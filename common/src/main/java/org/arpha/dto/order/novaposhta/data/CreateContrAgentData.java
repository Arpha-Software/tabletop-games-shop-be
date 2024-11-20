package org.arpha.dto.order.novaposhta.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContrAgentData {

    private String ref;
    private String description;
    private String firstName;
    private String lastName;
    private String counterParty;
    private String ownershipForm;
    private String ownershipFormDescription;
    private String edrpou;
    private String counterpartyType;
}
