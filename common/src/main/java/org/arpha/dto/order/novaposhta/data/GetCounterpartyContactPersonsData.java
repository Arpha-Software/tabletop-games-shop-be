package org.arpha.dto.order.novaposhta.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCounterpartyContactPersonsData {

    @JsonProperty("Description")
    private String description;
    @JsonProperty("Ref")
    private String ref;
    @JsonProperty("Phones")
    private String phones;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("MiddleName")
    private String middleName;

}
