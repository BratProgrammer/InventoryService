package com.example.InventoryService.DTO.Kafka;

import com.example.InventoryService.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderStatusResponse {

    private Long orderId;

    private Status orderStatus;

}
