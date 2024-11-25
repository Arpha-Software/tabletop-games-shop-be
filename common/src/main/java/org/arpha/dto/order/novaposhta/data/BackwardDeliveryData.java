package org.arpha.dto.order.novaposhta.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackwardDeliveryData {

    @JsonProperty("PayerType")
    private String payerType;
    @JsonProperty("CargoType")
    private String cargoType;
    @JsonProperty("RedeliveryString")
    private String redeliveryString;

    public BackwardDeliveryData(String redeliveryString) {
        this.payerType = "Recipient";
        this.cargoType = "Money";
        this.redeliveryString = redeliveryString;
    }
}
