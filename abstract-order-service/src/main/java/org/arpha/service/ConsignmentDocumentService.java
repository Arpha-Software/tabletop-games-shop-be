package org.arpha.service;

import org.arpha.dto.order.request.CreateConsignmentDocumentRequest;
import org.arpha.dto.order.request.CreateContrAgentRequest;
import org.arpha.dto.order.request.SearchSettlementsRequest;
import org.arpha.dto.order.request.SearchSettlementsStreetsRequest;
import org.arpha.dto.order.request.SearchWarehousesRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.CreateContrAgentResponse;
import org.arpha.dto.order.response.SearchSettlementsResponse;
import org.arpha.dto.order.response.SearchSettlementsStreetsResponse;
import org.arpha.dto.order.response.SearchWarehousesResponse;

import java.util.List;

public interface ConsignmentDocumentService {

    CreateConsignmentDocumentResponse createConsignmentDocument(CreateConsignmentDocumentRequest createConsignmentDocumentRequest);
    SearchSettlementsResponse searchSettlements(SearchSettlementsRequest searchSettlementsRequest);
    SearchSettlementsStreetsResponse searchSettlementsStreets(SearchSettlementsStreetsRequest searchSettlementsStreetsRequest);
    SearchWarehousesResponse searchWarehouses(SearchWarehousesRequest searchWarehousesRequest);
    CreateContrAgentResponse createContrAgent(CreateContrAgentRequest createContrAgentRequest);

}
