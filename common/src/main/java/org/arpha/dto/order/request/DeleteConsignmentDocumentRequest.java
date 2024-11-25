package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.DeleteConsignmentMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteConsignmentDocumentRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private DeleteConsignmentMethodProperties methodProperties;

    public DeleteConsignmentDocumentRequest(String apiKey, String documentRef) {
        this.apiKey = apiKey;
        this.modelName = "InternetDocumentGeneral";
        this.calledMethod = "delete";
        this.methodProperties = new DeleteConsignmentMethodProperties(documentRef);
    }
}
