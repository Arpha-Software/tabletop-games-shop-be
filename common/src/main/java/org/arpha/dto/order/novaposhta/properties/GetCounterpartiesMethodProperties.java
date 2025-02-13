package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCounterpartiesMethodProperties {

    @JsonProperty("CounterpartyProperty")
    private String counterpartyProperty;
    @JsonProperty("Page")
    private String page;

    public GetCounterpartiesMethodProperties() {
        this.counterpartyProperty = "Sender";
        this.page = "1";
    }

}
