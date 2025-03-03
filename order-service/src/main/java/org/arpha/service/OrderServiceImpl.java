package org.arpha.service;

import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.enums.DeliveryType;
import org.arpha.dto.order.enums.OrderStatus;
import org.arpha.dto.order.novaposhta.data.CreateContrAgentData;
import org.arpha.dto.order.novaposhta.properties.CreateConsignmentMethodProperties;
import org.arpha.dto.order.novaposhta.properties.CreateContrAgentMethodProperties;
import org.arpha.dto.order.request.CreateConsignmentDocumentRequest;
import org.arpha.dto.order.request.CreateConsignmentNovaPoshtaDocumentRequest;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.*;
import org.arpha.entity.Order;
import org.arpha.exception.CreateConsignmentDocumentException;
import org.arpha.exception.CreateOrderException;
import org.arpha.exception.OrderNotFoundException;
import org.arpha.mapper.ConsignmentDocumentMapper;
import org.arpha.mapper.OrderMapper;
import org.arpha.property.NovaPoshtaConsignmentProperties;
import org.arpha.repository.OrderRepository;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.arpha.dto.order.enums.DeliveryType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    public static final String ORDER_NOT_FOUND_MESSAGE = "Order with %s number doesn't exist!";

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ConsignmentDocumentMapper consignmentDocumentMapper;
    private final NovaPoshtaConsignmentProperties novaPoshtaConsignmentProperties;
    private final ConsignmentDocumentService consignmentDocumentService;

    @Override
    public OrderInfoResponse createOrder(CreateOrderRequest createOrderRequest) {
        return Boxed
                .of(createOrderRequest)
                .mapToBoxed(orderMapper::toOrder)
                .mapToBoxed(orderRepository::save)
                .mapToBoxed(orderMapper::toOrderInfoResponse)
                .orElseThrow(() -> new CreateOrderException("Not enough quantity of item in store"));
    }

    @Override
    public Page<OrderDetailsResponse> getsOrders(Predicate predicate, Pageable pageable) {
        return orderRepository.findAll(predicate, pageable).map(orderMapper::toOrderDetailsResponse);
    }

    @Override
    public OrderInfoResponse getById(long orderId) {
        return Boxed
                .of(orderId)
                .flatOpt(orderRepository::findById)
                .mapToBoxed(orderMapper::toOrderInfoResponse)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE.formatted(orderId)));
    }

    @Override
    public Page<OrderDetailsResponse> getUsersOrders(long userId, Predicate predicate, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, predicate, pageable).map(orderMapper::toOrderDetailsResponse);

    }

    @Override
    public OrderInfoResponse createConsignmentDocument(CreateConsignmentDocumentRequest documentRequest) {
        Order order = orderRepository.findById(documentRequest.getOrderId()).orElseThrow(() ->
                new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE.formatted(documentRequest.getOrderId())));
        CreateConsignmentDocumentResponse createConsignmentDocumentResponse;
        if (List.of(NOVA_POSHTA_DEPARTMENT, NOVA_POSHTA_POSHTMAT).contains(order.getDeliveryDetails().getDeliveryType())) {
            createConsignmentDocumentResponse = createToWarehouseDeliveryDocument(order, documentRequest);
        } else if (NOVA_POSHTA_COURIER.equals(order.getDeliveryDetails().getDeliveryType())) {
            createConsignmentDocumentResponse = createToHouseDeliveryDocument(order, documentRequest);
        } else {
            throw new CreateConsignmentDocumentException(("Consignment document can't be created for %s order because it's" +
                    " wrong delivery type!").formatted(order.getId()));
        }
        orderMapper.addDocumentDataToOrder(order, createConsignmentDocumentResponse.getData().getFirst());
        orderRepository.save(order);
        return orderMapper.toOrderInfoResponse(order);

    }

    private CreateConsignmentDocumentResponse createToWarehouseDeliveryDocument(
            Order order, CreateConsignmentDocumentRequest documentRequest) {
        CreateContrAgentMethodProperties contrAgentMethodProperties = consignmentDocumentMapper
                .toCreateContrAgentMethodProperties(order);
        CreateContrAgentResponse createContrAgentResponse = consignmentDocumentService
                .createContrAgent(contrAgentMethodProperties);

        GetCounterpartiesResponse senderResponse = consignmentDocumentService.getCounterparties();
        String senderRef = senderResponse.getData().getFirst().getRef();
        GetCounterpartyContactPersonsResponse contactSenderResponse = consignmentDocumentService
                .getCounterpartyContactPersons(senderRef);

        CreateConsignmentMethodProperties createConsignmentMethodProperties = consignmentDocumentMapper
                .toCreateConsignmentMethodProperties(order, senderResponse, contactSenderResponse,
                        createContrAgentResponse.getData().getFirst(), documentRequest);

        return consignmentDocumentService
                .createConsignmentDocument(new CreateConsignmentNovaPoshtaDocumentRequest(
                        novaPoshtaConsignmentProperties.apiKey(), createConsignmentMethodProperties));
    }

    private CreateConsignmentDocumentResponse createToHouseDeliveryDocument(Order order, CreateConsignmentDocumentRequest
            documentRequest) {
        CreateContrAgentMethodProperties contrAgentMethodProperties = consignmentDocumentMapper
                .toCreateContrAgentMethodProperties(order);
        CreateContrAgentResponse createContrAgentResponse = consignmentDocumentService
                .createContrAgent(contrAgentMethodProperties);
        CreateContrAgentData createContrAgentData = createContrAgentResponse.getData().getFirst();

        CreateHomeAddressResponse createHomeAddressResponse = consignmentDocumentService.createHomeAddress(
                consignmentDocumentMapper.toCreateHomeAddressMethodProperties(createContrAgentData.getRef(),
                        order.getDeliveryDetails().getDeliveryAddress()));
        GetCounterpartiesResponse senderResponse = consignmentDocumentService.getCounterparties();
        String senderRef = senderResponse.getData().getFirst().getRef();
        GetCounterpartyContactPersonsResponse contactSenderResponse = consignmentDocumentService
                .getCounterpartyContactPersons(senderRef);

        CreateConsignmentMethodProperties createConsignmentMethodProperties = consignmentDocumentMapper
                .toCreateConsignmentMethodProperties(order, senderResponse, contactSenderResponse, createContrAgentResponse
                        .getData().getFirst(), createHomeAddressResponse.getData().getFirst(), documentRequest);

        return consignmentDocumentService
                .createConsignmentDocument(new CreateConsignmentNovaPoshtaDocumentRequest(
                        novaPoshtaConsignmentProperties.apiKey(), createConsignmentMethodProperties));
    }

    @Override
    public void cancelOrder(long orderId) {
        Boxed
                .of(orderId)
                .flatOpt(orderRepository::findById)
                .doIfTrue(order -> List.of(DeliveryType.NOVA_POSHTA_COURIER, NOVA_POSHTA_POSHTMAT,
                                NOVA_POSHTA_DEPARTMENT).contains(order.getDeliveryDetails().getDeliveryType()) &&
                                order.getOrderStatus() == OrderStatus.CREATED_CONSIGNMENT,
                        order -> consignmentDocumentService.deleteConsignment(order.getDeliveryDetails().getDocumentRef()))
                .doWith(this::returnQuantities)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE.formatted(orderId)));
    }

    private void returnQuantities(Order order) {
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.getOrderedItems().forEach(orderItem -> orderItem.getProduct().addQuantity(orderItem.getQuantity()));
        orderRepository.save(order);
    }
}
