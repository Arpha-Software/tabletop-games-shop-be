package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.GetNovaPoshtaTrackingMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNovaPoshtaTrackingRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private GetNovaPoshtaTrackingMethodProperties methodProperties;

    public GetNovaPoshtaTrackingRequest(String apiKey, GetNovaPoshtaTrackingMethodProperties methodProperties) {
        this.modelName = "TrackingDocumentGeneral";
        this.calledMethod = "getStatusDocuments";
        this.methodProperties = methodProperties;
        this.apiKey = apiKey;
    }
}
