package com.example.InventoryService.DTO.Kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestItem {

    private Long productId;
    private int requestedQuantity;

}
