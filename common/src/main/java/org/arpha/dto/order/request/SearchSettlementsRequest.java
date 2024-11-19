package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.SearchSettlementsProperties;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsRequest {

    @Value("${nova-poshta.config.apiKey}")
    private String apiKey;
    private String modelName;
    private String calledMethod;
    private SearchSettlementsProperties methodProperties;

    public SearchSettlementsRequest(SearchSettlementsProperties methodProperties) {
        this.modelName = "AddressGeneral";
        this.calledMethod = "searchSettlements";
        this.methodProperties = methodProperties;
    }
}
