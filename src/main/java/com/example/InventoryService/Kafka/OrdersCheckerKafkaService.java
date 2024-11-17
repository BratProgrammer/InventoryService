package com.example.InventoryService.Kafka;

import com.example.InventoryService.DTO.Kafka.OrderRequest;
import com.example.InventoryService.DTO.Kafka.OrderStatusResponse;
import com.example.InventoryService.Enums.Status;
import com.example.InventoryService.Services.OrderCheckerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersCheckerKafkaService {

    private final KafkaTemplate<String, OrderStatusResponse> kafkaTemplate;

    private final OrderCheckerService orderCheckerService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orders_requests", groupId = "3")
    private void getNewOrderRequest(String message) throws JsonProcessingException {
        OrderRequest orderRequest = objectMapper.readValue(message, OrderRequest.class);
        if (orderCheckerService.isOrderPossible(orderRequest)) {
            kafkaTemplate.send("order_statuses", new OrderStatusResponse(orderRequest.getOrderId(), Status.ACCEPTED));
        } else {
            kafkaTemplate.send("order_statuses", new OrderStatusResponse(orderRequest.getOrderId(), Status.REJECTED));
        }
    }


}
