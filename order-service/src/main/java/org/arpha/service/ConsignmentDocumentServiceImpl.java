package org.arpha.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
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
import org.arpha.property.NovaPoshtaConsignmentProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ConsignmentDocumentServiceImpl implements ConsignmentDocumentService {

    private final RestClient restClient;
    private final NovaPoshtaConsignmentProperties novaPoshtaConsignmentProperties;

    @Override
    public CreateConsignmentDocumentResponse createConsignmentDocument(CreateConsignmentDocumentRequest createConsignmentDocumentRequest) {
        return null;

    }

    @Override
    public SearchSettlementsResponse searchSettlements(SearchSettlementsRequest searchSettlementsRequest) {
        return restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchSettlementsRequest)
                .retrieve()
                .toEntity(SearchSettlementsResponse.class)
                .getBody();
    }

    @Override
    public SearchSettlementsStreetsResponse searchSettlementsStreets(SearchSettlementsStreetsRequest searchSettlementsStreetsRequest) {
        return restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchSettlementsStreetsRequest)
                .retrieve()
                .toEntity(SearchSettlementsStreetsResponse.class)
                .getBody();
    }

    @Override
    public SearchWarehousesResponse searchWarehouses(SearchWarehousesRequest searchWarehousesRequest) {
        return restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchWarehousesRequest)
                .retrieve()
                .toEntity(SearchWarehousesResponse.class)
                .getBody();
    }

    @Override
    public CreateContrAgentResponse createContrAgent(CreateContrAgentRequest createContrAgentRequest) {
        return restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(createContrAgentRequest)
                .retrieve()
                .toEntity(CreateContrAgentResponse.class)
                .getBody();
    }
}
