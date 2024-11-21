package org.arpha.service;

import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsStreetsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.CreateContrAgentResponse;
import org.arpha.dto.order.response.SearchSettlementsResponse;
import org.arpha.dto.order.response.SearchSettlementsStreetsResponse;
import org.arpha.dto.order.response.SearchWarehousesResponse;

import java.util.List;

public interface ConsignmentDocumentService {

    CreateConsignmentDocumentResponse createConsignmentDocument(CreateOrderRequest createOrderRequest);
    SearchSettlementsResponse searchSettlements(SearchSettlementsProperties searchSettlementsProperties);
    SearchSettlementsStreetsResponse searchSettlementsStreets(SearchSettlementsStreetsProperties settlementsStreetsProperties);
    SearchWarehousesResponse searchWarehouses(SearchWarehouseMethodProperties warehouseMethodProperties);
    CreateContrAgentResponse createContrAgent(CreateContrAgentMethodProperties contrAgentMethodProperties);

}
