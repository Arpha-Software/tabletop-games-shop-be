package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.GetCounterpartyContactPersonsMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCounterpartyContactPersonsRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private GetCounterpartyContactPersonsMethodProperties methodProperties;

    public GetCounterpartyContactPersonsRequest(String apiKey, String counterpartyRef) {
        this.apiKey = apiKey;
        this.modelName = "CounterpartyGeneral";
        this.calledMethod = "getCounterpartyContactPersons";
        this.methodProperties = new GetCounterpartyContactPersonsMethodProperties(counterpartyRef);
    }

}
