package org.arpha.scheduler;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.novaposhta.GetNovaPoshtaTrackingDocument;
import org.arpha.dto.order.novaposhta.data.GetNovaPoshtaTrackingData;
import org.arpha.dto.order.novaposhta.properties.GetNovaPoshtaTrackingMethodProperties;
import org.arpha.dto.order.response.GetNovaPoshtaTrackingResponse;
import org.arpha.entity.Order;
import org.arpha.mapper.ConsignmentDocumentMapper;
import org.arpha.repository.OrderRepository;
import org.arpha.service.ConsignmentDocumentService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.arpha.dto.order.enums.OrderStatus.CREATED_CONSIGNMENT;
import static org.arpha.dto.order.enums.OrderStatus.DELIVERING;
import static org.arpha.dto.order.enums.OrderStatus.WAITING_TAKEOUT;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "orders.novaposhta.statusScheduler.enabled", havingValue = "true")
public class NovaPoshtaStatusScheduler {

    private final OrderRepository orderRepository;
    private final ConsignmentDocumentService consignmentDocumentService;
    private final ConsignmentDocumentMapper consignmentDocumentMapper;

    @Scheduled(cron = "${orders.novaposhta.statusScheduler.cron}")
    public void run() {
        List<Order> orders = orderRepository.findAllByOrderStatusIsIn(List.of(CREATED_CONSIGNMENT, DELIVERING, WAITING_TAKEOUT));
        List<GetNovaPoshtaTrackingDocument> documents = consignmentDocumentMapper.toGetNovaPoshtaTrackingDocuments(orders);
        List<GetNovaPoshtaTrackingData> data = consignmentDocumentService.getGetNovaPoshtaTracking(new GetNovaPoshtaTrackingMethodProperties(documents))
                .getData();

    }
}
