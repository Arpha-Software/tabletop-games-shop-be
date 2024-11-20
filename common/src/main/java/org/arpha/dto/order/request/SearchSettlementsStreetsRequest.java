package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsStreetsProperties;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsStreetsRequest {

    @Value("${nova-poshta.config.apiKey}")
    private String apiKey;
    private String modelName;
    private String calledMethod;
    private SearchSettlementsStreetsProperties methodProperties;

    public SearchSettlementsStreetsRequest(SearchSettlementsStreetsProperties methodProperties) {
        this.modelName = "AddressGeneral";
        this.calledMethod = "searchSettlementStreets";
        this.methodProperties = methodProperties;
    }
}
