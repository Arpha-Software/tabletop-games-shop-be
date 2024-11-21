package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsStreetsProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsStreetsRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private SearchSettlementsStreetsProperties methodProperties;

    public SearchSettlementsStreetsRequest(String apiKey, SearchSettlementsStreetsProperties methodProperties) {
        this.apiKey = apiKey;
        this.modelName = "AddressGeneral";
        this.calledMethod = "searchSettlementStreets";
        this.methodProperties = methodProperties;
    }
}
