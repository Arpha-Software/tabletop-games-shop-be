package org.arpha.scheduler;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.arpha.dto.order.enums.OrderStatus;
import org.arpha.dto.order.novaposhta.GetNovaPoshtaTrackingDocument;
import org.arpha.dto.order.novaposhta.data.GetNovaPoshtaTrackingData;
import org.arpha.dto.order.novaposhta.properties.GetNovaPoshtaTrackingMethodProperties;
import org.arpha.entity.DeliveryDetails;
import org.arpha.entity.Order;
import org.arpha.entity.OrderStatusHistory;
import org.arpha.mapper.ConsignmentDocumentMapper;
import org.arpha.repository.OrderRepository;
import org.arpha.service.ConsignmentDocumentService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.arpha.dto.order.enums.OrderStatus.*;


@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "orders.novaposhta.statusScheduler.enabled", havingValue = "true")
public class NovaPoshtaStatusScheduler {

    private static final Map<String, OrderStatus> CODE_TO_STATUS_MAP = new HashMap<>();

    static {
        CODE_TO_STATUS_MAP.put("1", CREATED_CONSIGNMENT);
        CODE_TO_STATUS_MAP.put("2", CANCELLED);
        CODE_TO_STATUS_MAP.put("3", CONSIGNMENT_NOT_FOUND);
        CODE_TO_STATUS_MAP.put("4", IN_CITY_INTERREGIONAL);
        CODE_TO_STATUS_MAP.put("41", IN_CITY_LOCAL);
        CODE_TO_STATUS_MAP.put("5", HEADING_TO_CITY);
        CODE_TO_STATUS_MAP.put("6", IN_CITY_ESTIMATED_DELIVERY);
        CODE_TO_STATUS_MAP.put("7", ARRIVED_AT_WAREHOUSE);
        CODE_TO_STATUS_MAP.put("8", ARRIVED_AT_LOCKER);
        CODE_TO_STATUS_MAP.put("9", RECEIVED);
        CODE_TO_STATUS_MAP.put("10", RECEIVED_PENDING_PAYMENT);
        CODE_TO_STATUS_MAP.put("11", RECEIVED_PAYMENT_ISSUED);
        CODE_TO_STATUS_MAP.put("12", PROCESSING);
        CODE_TO_STATUS_MAP.put("101", ON_THE_WAY);
        CODE_TO_STATUS_MAP.put("102", REFUSED_RETURN_ORDERED);
        CODE_TO_STATUS_MAP.put("103", REFUSED);
        CODE_TO_STATUS_MAP.put("104", ADDRESS_CHANGED);
        CODE_TO_STATUS_MAP.put("105", STORAGE_ENDED);
        CODE_TO_STATUS_MAP.put("106", REVERSE_DELIVERY_CREATED);
        CODE_TO_STATUS_MAP.put("111", FAILED_DELIVERY_NO_CONTACT);
        CODE_TO_STATUS_MAP.put("112", DELIVERY_DATE_CHANGED);
    }

    private final OrderRepository orderRepository;
    private final ConsignmentDocumentService consignmentDocumentService;
    private final ConsignmentDocumentMapper consignmentDocumentMapper;

    @Scheduled(cron = "${orders.novaposhta.statusScheduler.cron}")
    public void run() {
        List<Order> orders = orderRepository.findAllByOrderStatusIsIn(List.of(CREATED_CONSIGNMENT, ON_THE_WAY, WAITING_TAKEOUT,
                IN_CITY_INTERREGIONAL, IN_CITY_LOCAL, HEADING_TO_CITY, IN_CITY_ESTIMATED_DELIVERY, ARRIVED_AT_WAREHOUSE,
                ARRIVED_AT_LOCKER, PROCESSING, RECEIVED_PENDING_PAYMENT, RECEIVED_PAYMENT_ISSUED, REFUSED_RETURN_ORDERED,
                ADDRESS_CHANGED, STORAGE_ENDED, REVERSE_DELIVERY_CREATED, FAILED_DELIVERY_NO_CONTACT, DELIVERY_DATE_CHANGED));
        if (!orders.isEmpty()) {
            List<GetNovaPoshtaTrackingDocument> documents = consignmentDocumentMapper.toGetNovaPoshtaTrackingDocuments(orders);
            List<GetNovaPoshtaTrackingData> data = consignmentDocumentService.getGetNovaPoshtaTracking(new GetNovaPoshtaTrackingMethodProperties(documents))
                    .getData();
            orderRepository.saveAll(updateOrdersStatus(orders, data));
        }
    }

    private List<Order> updateOrdersStatus(List<Order> orders, List<GetNovaPoshtaTrackingData> data) {
        return orders
                .stream()
                .map(order -> Pair.of(order, findTrackingData(order.getDeliveryDetails(), data)))
                .filter(pair -> isStatusChanged(pair.getLeft(), pair.getRight()))
                .map(pair -> updateStatus(pair.getLeft(), pair.getRight()))
                .toList();
    }

    private Order updateStatus(Order order, GetNovaPoshtaTrackingData trackingData) {
        OrderStatus newStatus = CODE_TO_STATUS_MAP.get(trackingData.getStatusCode());
        OrderStatusHistory statusHistory = OrderStatusHistory.builder()
                .order(order)
                .status(newStatus)
                .notes(trackingData.getStatus())
                .build();
        order.setOrderStatus(CODE_TO_STATUS_MAP.get(trackingData.getStatusCode()));
        order.getStatusHistory().add(statusHistory);
        return order;
    }

    private boolean isStatusChanged(Order order, GetNovaPoshtaTrackingData trackingData) {
        return !order.getOrderStatus().equals(CODE_TO_STATUS_MAP.get(trackingData.getStatusCode()));
    }

    private GetNovaPoshtaTrackingData findTrackingData(DeliveryDetails deliveryDetails, List<GetNovaPoshtaTrackingData> data) {
        return data
                .stream()
                .filter(tracking -> deliveryDetails.getDocNumber().equalsIgnoreCase(tracking.getNumber()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Couldn't find Consignment document for order number %s."
                        .formatted(deliveryDetails.getDocNumber())));
    }


}
