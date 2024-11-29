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
import org.arpha.exception.NovaPoshtaApiException;
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
    public CreateConsignmentDocumentResponse createConsignmentDocument(CreateConsignmentNovaPoshtaDocumentRequest request) {
        CreateConsignmentDocumentResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .body(request)
                .retrieve()
                .body(CreateConsignmentDocumentResponse.class);
        if (!response.isSuccess()) {
            throw new NovaPoshtaApiException("Exception happened during consignment document creation. Reasons: " +
                                             String.join(",", response.getErrors()));
        }
        return response;
    }


    @Override
    public SearchSettlementsResponse searchSettlements(SearchSettlementsProperties settlementsProperties) {
        SearchSettlementsResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SearchSettlementsRequest(novaPoshtaConsignmentProperties.apiKey(), settlementsProperties))
                .retrieve()
                .toEntity(SearchSettlementsResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new NovaPoshtaApiException("Exception happened during settlements search. Reasons: " +
                                             String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public SearchSettlementsStreetsResponse searchSettlementsStreets(SearchSettlementsStreetsProperties properties) {
        SearchSettlementsStreetsResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SearchSettlementsStreetsRequest(novaPoshtaConsignmentProperties.apiKey(), properties))
                .retrieve()
                .toEntity(SearchSettlementsStreetsResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new NovaPoshtaApiException("Exception happened during streets search. Reasons: " +
                                             String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public SearchWarehousesResponse searchWarehouses(SearchWarehouseMethodProperties warehouseMethodProperties) {
        SearchWarehousesResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SearchWarehousesRequest(novaPoshtaConsignmentProperties.apiKey(), warehouseMethodProperties))
                .retrieve()
                .body(SearchWarehousesResponse.class);
        if (!response.isSuccess()) {
            throw new NovaPoshtaApiException("Exception happened during warehouses search. Reasons: " +
                                             String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public void deleteConsignment(String documentRef) {
        DeleteConsignmentDocumentResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DeleteConsignmentDocumentRequest(novaPoshtaConsignmentProperties.apiKey(), documentRef))
                .retrieve()
                .body(DeleteConsignmentDocumentResponse.class);
        if (!response.isSuccess()) {
            throw new NovaPoshtaApiException("Exception happened during consignment document deletion. Reasons: " +
                                             String.join(",", response.getErrors()));
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
            throw new NovaPoshtaApiException("Exception happened during counterparties searching. Reasons: " +
                                             String.join(",", response.getErrors()));
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
            throw new NovaPoshtaApiException("Exception happened during contact person of counterparty searching. Reasons: " +
                                             String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public CreateContrAgentResponse createContrAgent(CreateContrAgentMethodProperties contrAgentMethodProperties) {
        CreateContrAgentResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateContrAgentRequest(novaPoshtaConsignmentProperties.apiKey(), contrAgentMethodProperties))
                .retrieve()
                .toEntity(CreateContrAgentResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new NovaPoshtaApiException("Exception happened during contr agent creation. Reasons: " +
                                             String.join(",", response.getErrors()));
        }
        return response;
    }

    @Override
    public CreateHomeAddressResponse createHomeAddressResponse(CreateHomeAddressMethodProperties methodProperties) {
        CreateHomeAddressResponse response = restClient
                .post()
                .uri(novaPoshtaConsignmentProperties.apiUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateHomeAddressRequest(novaPoshtaConsignmentProperties.apiKey(), methodProperties))
                .retrieve()
                .toEntity(CreateHomeAddressResponse.class)
                .getBody();
        if (!response.isSuccess()) {
            throw new NovaPoshtaApiException("Exception happened during home address creation for user. Reasons: " +
                                             String.join(",", response.getErrors()));
        }
        return response;
    }
}
