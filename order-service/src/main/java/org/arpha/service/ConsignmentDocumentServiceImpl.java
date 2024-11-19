package org.arpha.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.request.CreateConsignmentDocumentRequest;
import org.arpha.dto.order.request.SearchSettlementsRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.SearchSettlementsResponse;
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
}
