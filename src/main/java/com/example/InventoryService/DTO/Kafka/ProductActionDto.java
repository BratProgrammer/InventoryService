package com.example.InventoryService.DTO.Kafka;

import com.example.InventoryService.Enums.ProductAction;
import lombok.Data;

@Data
public class ProductActionDto {
    private Long id;
    private ProductAction action;
}
