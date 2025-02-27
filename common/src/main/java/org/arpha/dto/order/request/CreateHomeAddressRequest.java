package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.CreateHomeAddressMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeAddressRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private CreateHomeAddressMethodProperties methodProperties;

    public CreateHomeAddressRequest(String apiKey, CreateHomeAddressMethodProperties methodProperties) {
        this.apiKey = apiKey;
        this.modelName = "AddressGeneral";
        this.calledMethod = "save";
        this.methodProperties = methodProperties;
    }
}
