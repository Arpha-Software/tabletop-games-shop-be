package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContrAgentRequest {

    @Value("${nova-poshta.config.apiKey}")
    private String apiKey;
    private String modelName;
    private String calledMethod;
    private CreateContrAgentMethodProperties methodProperties;

    public CreateContrAgentRequest(CreateContrAgentMethodProperties methodProperties) {
        this.modelName = "CounterpartyGeneral";
        this.calledMethod = "save";
        this.methodProperties = methodProperties;
    }
}
