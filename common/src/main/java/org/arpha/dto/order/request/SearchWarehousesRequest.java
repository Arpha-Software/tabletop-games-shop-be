package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchWarehousesRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private SearchWarehouseMethodProperties methodProperties;

    public SearchWarehousesRequest(String apiKey, SearchWarehouseMethodProperties methodProperties) {
        this.apiKey = apiKey;
        this.modelName = "AddressGeneral";
        this.calledMethod = "getWarehouses";
        this.methodProperties = methodProperties;
    }

}
