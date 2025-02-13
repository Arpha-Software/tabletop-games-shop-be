package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.CreateConsignmentMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentNovaPoshtaDocumentRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private CreateConsignmentMethodProperties methodProperties;

    public CreateConsignmentNovaPoshtaDocumentRequest(String apiKey, CreateConsignmentMethodProperties methodProperties) {
        this.modelName = "InternetDocumentGeneral";
        this.calledMethod = "save";
        this.apiKey = apiKey;
        this.methodProperties = methodProperties;
    }
}
