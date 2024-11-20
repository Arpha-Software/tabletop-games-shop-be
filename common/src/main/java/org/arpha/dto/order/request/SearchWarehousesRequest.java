package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchWarehousesRequest {

    @Value("${nova-poshta.config.apiKey}")
    private String apiKey;
    private String modelName;
    private String calledMethod;
    private SearchWarehouseMethodProperties methodProperties;

    public SearchWarehousesRequest(SearchWarehouseMethodProperties methodProperties) {
        this.modelName = "AddressGeneral";
        this.calledMethod = "getWarehouses";
        this.methodProperties = methodProperties;
    }

}
