package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.CreateHomeAddressMethodProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsStreetsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.arpha.dto.order.request.CreateConsignmentNovaPoshtaDocumentRequest;
import org.arpha.dto.order.request.CreateContrAgentRequest;
import org.arpha.dto.order.request.CreateHomeAddressRequest;
import org.arpha.dto.order.request.DeleteConsignmentDocumentRequest;
import org.arpha.dto.order.request.GetCounterpartiesRequest;
import org.arpha.dto.order.request.GetCounterpartyContactPersonsRequest;
import org.arpha.dto.order.request.SearchSettlementsRequest;
import org.arpha.dto.order.request.SearchSettlementsStreetsRequest;
import org.arpha.dto.order.request.SearchWarehousesRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.CreateContrAgentResponse;
import org.arpha.dto.order.response.CreateHomeAddressResponse;
import org.arpha.dto.order.response.DeleteConsignmentDocumentResponse;
import org.arpha.dto.order.response.GetCounterpartiesResponse;
import org.arpha.dto.order.response.GetCounterpartyContactPersonsResponse;
import org.arpha.dto.order.response.SearchSettlementsResponse;
import org.arpha.dto.order.response.SearchSettlementsStreetsResponse;
import org.arpha.dto.order.response.SearchWarehousesResponse;
import org.arpha.exception.CreateConsignmentDocumentException;
import org.arpha.exception.CreateContrAgentException;
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
    public CreateConsignmentDocumentResponse createConsignmentDocument(CreateConsignmentNovaPoshtaDocumentRequest consignmentDocumentRequest) {
        CreateConsignmentDocumentResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .body(consignmentDocumentRequest)
                .retrieve()
                .body(CreateConsignmentDocumentResponse.class);
        if (!response.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", response.getErrors()));
        }
        return response;
    }


    @Override
    public SearchSettlementsResponse searchSettlements(SearchSettlementsProperties searchSettlementsProperties) {
        SearchSettlementsResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SearchSettlementsRequest(novaPoshtaConsignmentProperties.apiKey(), searchSettlementsProperties))
                .retrieve()
                .toEntity(SearchSettlementsResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public SearchSettlementsStreetsResponse searchSettlementsStreets(SearchSettlementsStreetsProperties settlementsStreetsProperties) {
        SearchSettlementsStreetsResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SearchSettlementsStreetsRequest(novaPoshtaConsignmentProperties.apiKey(), settlementsStreetsProperties))
                .retrieve()
                .toEntity(SearchSettlementsStreetsResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public SearchWarehousesResponse searchWarehouses(SearchWarehouseMethodProperties warehouseMethodProperties) {
        SearchWarehousesResponse response =  restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SearchWarehousesRequest(novaPoshtaConsignmentProperties.apiKey(), warehouseMethodProperties))
                .retrieve()
                .body(SearchWarehousesResponse.class);
        if (!response.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public void deleteConsignment(String documentRef) {
        DeleteConsignmentDocumentResponse deleteConsignmentDocumentResponse = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DeleteConsignmentDocumentRequest(novaPoshtaConsignmentProperties.apiKey(), documentRef))
                .retrieve()
                .body(DeleteConsignmentDocumentResponse.class);
        if (!deleteConsignmentDocumentResponse.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", deleteConsignmentDocumentResponse.getErrors()));
        }
    }

    @Override
    public GetCounterpartiesResponse getCounterparties() {
        GetCounterpartiesResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new GetCounterpartiesRequest(novaPoshtaConsignmentProperties.apiKey()))
                .retrieve()
                .body(GetCounterpartiesResponse.class);
        if (!response.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", response.getErrors()));
        }

        return response;
    }

    @Override
    public GetCounterpartyContactPersonsResponse getCounterpartyContactPersons(String counterpartyRef) {
        GetCounterpartyContactPersonsResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new GetCounterpartyContactPersonsRequest(novaPoshtaConsignmentProperties.apiKey(), counterpartyRef))
                .retrieve()
                .toEntity(GetCounterpartyContactPersonsResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public CreateContrAgentResponse createContrAgent(CreateContrAgentMethodProperties contrAgentMethodProperties) {
        CreateContrAgentResponse createContrAgentResponse = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateContrAgentRequest(novaPoshtaConsignmentProperties.apiKey(), contrAgentMethodProperties))
                .retrieve()
                .toEntity(CreateContrAgentResponse.class)
                .getBody();
        if (!createContrAgentResponse.isSuccess()) {
            throw new CreateContrAgentException(String.join(",", createContrAgentResponse.getErrors()));
        }
        return createContrAgentResponse;
    }

    @Override
    public CreateHomeAddressResponse createHomeAddressResponse(CreateHomeAddressMethodProperties createHomeAddressMethodProperties) {
        CreateHomeAddressResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateHomeAddressRequest(novaPoshtaConsignmentProperties.apiKey(), createHomeAddressMethodProperties))
                .retrieve()
                .toEntity(CreateHomeAddressResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new CreateConsignmentDocumentException(String.join(",", response.getErrors()));
        }
        return response;
    }
}
