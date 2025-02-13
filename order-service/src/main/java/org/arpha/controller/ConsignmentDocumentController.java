package org.arpha.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchSettlementsStreetsProperties;
import org.arpha.dto.order.novaposhta.properties.SearchWarehouseMethodProperties;
import org.arpha.dto.order.response.CreateContrAgentResponse;
import org.arpha.dto.order.response.SearchSettlementsResponse;
import org.arpha.dto.order.response.SearchSettlementsStreetsResponse;
import org.arpha.dto.order.response.SearchWarehousesResponse;
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
        return consignmentDocumentService.searchSettlements(searchSettlementsRequest);
    }


    @PostMapping("/settlements/streets/search")
    public SearchSettlementsStreetsResponse searchSettlementsStreets(@RequestBody SearchSettlementsStreetsProperties searchSettlementsProperties) {
        return consignmentDocumentService.searchSettlementsStreets(searchSettlementsProperties);
    }

    @PostMapping("/warehouses/search")
    public SearchWarehousesResponse searchWarehouses(@RequestBody SearchWarehouseMethodProperties warehouseMethodProperties) {
        return consignmentDocumentService.searchWarehouses(warehouseMethodProperties);
    }

    @PostMapping("/contr-agents")
    public CreateContrAgentResponse createContrAgent(@RequestBody CreateContrAgentMethodProperties contrAgentMethodProperties) {
        return consignmentDocumentService.createContrAgent(contrAgentMethodProperties);
    }
}
