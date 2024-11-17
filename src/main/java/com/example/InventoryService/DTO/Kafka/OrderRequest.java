package com.example.InventoryService.DTO.Kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequest {

    private Long orderId;

    private List<OrderRequestItem> orderRequestItems;

}
