package org.arpha.service;

import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.CreateHomeAddressMethodProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsStreetsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.arpha.dto.order.request.CreateConsignmentNovaPoshtaDocumentRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.CreateContrAgentResponse;
import org.arpha.dto.order.response.CreateHomeAddressResponse;
import org.arpha.dto.order.response.GetCounterpartiesResponse;
import org.arpha.dto.order.response.GetCounterpartyContactPersonsResponse;
import org.arpha.dto.order.response.SearchSettlementsResponse;
import org.arpha.dto.order.response.SearchSettlementsStreetsResponse;
import org.arpha.dto.order.response.SearchWarehousesResponse;

public interface ConsignmentDocumentService {

    CreateConsignmentDocumentResponse createConsignmentDocument(CreateConsignmentNovaPoshtaDocumentRequest consignmentDocumentRequest);

    SearchSettlementsResponse searchSettlements(SearchSettlementsProperties searchSettlementsProperties);

    SearchSettlementsStreetsResponse searchSettlementsStreets(SearchSettlementsStreetsProperties settlementsStreetsProperties);

    SearchWarehousesResponse searchWarehouses(SearchWarehouseMethodProperties warehouseMethodProperties);

    CreateContrAgentResponse createContrAgent(CreateContrAgentMethodProperties contrAgentMethodProperties);

    void deleteConsignment(String documentRef);

    GetCounterpartiesResponse getCounterparties();

    GetCounterpartyContactPersonsResponse getCounterpartyContactPersons(String counterpartyRef);

    CreateHomeAddressResponse createHomeAddress(CreateHomeAddressMethodProperties createHomeAddressMethodProperties);

}
