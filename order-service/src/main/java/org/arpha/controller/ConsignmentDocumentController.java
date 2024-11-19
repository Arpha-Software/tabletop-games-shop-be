package org.arpha.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.SearchSettlementsProperties;
import org.arpha.dto.order.request.SearchSettlementsRequest;
import org.arpha.dto.order.response.SearchSettlementsResponse;
import org.arpha.service.ConsignmentDocumentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/nova-poshta")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
public class ConsignmentDocumentController {

    private final ConsignmentDocumentService consignmentDocumentService;

    @PostMapping("/settlements/search")
    public SearchSettlementsResponse searchSettlements(@RequestBody SearchSettlementsProperties searchSettlementsRequest) {
        return consignmentDocumentService.searchSettlements(new SearchSettlementsRequest(searchSettlementsRequest));
    }
}
