package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCounterpartyContactPersonsMethodProperties {

    @JsonProperty("Ref")
    private String ref;
    @JsonProperty("Page")
    private String page;

    public GetCounterpartyContactPersonsMethodProperties(String ref) {
        this.ref = ref;
        this.page = "1";
    }

}
