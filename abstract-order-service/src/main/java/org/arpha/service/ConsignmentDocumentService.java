package org.arpha.service;

import org.arpha.dto.order.request.CreateConsignmentDocumentRequest;
import org.arpha.dto.order.request.SearchSettlementsRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.SearchSettlementsResponse;

import java.util.List;

public interface ConsignmentDocumentService {

    CreateConsignmentDocumentResponse createConsignmentDocument(CreateConsignmentDocumentRequest createConsignmentDocumentRequest);
    SearchSettlementsResponse searchSettlements(SearchSettlementsRequest searchSettlementsRequest);

}
