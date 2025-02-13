package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContrAgentRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private CreateContrAgentMethodProperties methodProperties;

    public CreateContrAgentRequest(String apiKey, CreateContrAgentMethodProperties methodProperties) {
        this.apiKey = apiKey;
        this.modelName = "CounterpartyGeneral";
        this.calledMethod = "save";
        this.methodProperties = methodProperties;
    }
}
