package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.GetCounterpartiesMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCounterpartiesRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private GetCounterpartiesMethodProperties methodProperties;

    public GetCounterpartiesRequest(String apiKey) {
        this.apiKey = apiKey;
        this.modelName = "CounterpartyGeneral";
        this.calledMethod = "getCounterparties";
        this.methodProperties = new GetCounterpartiesMethodProperties();
    }

}
