package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private SearchSettlementsProperties methodProperties;

    public SearchSettlementsRequest(String apiKey, SearchSettlementsProperties methodProperties) {
        this.apiKey = apiKey;
        this.modelName = "AddressGeneral";
        this.calledMethod = "searchSettlements";
        this.methodProperties = methodProperties;
    }
}
