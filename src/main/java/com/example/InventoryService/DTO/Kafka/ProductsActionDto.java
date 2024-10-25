package com.example.InventoryService.DTO.Kafka;

import com.example.InventoryService.Enums.ProductAction;
import lombok.Data;

import java.util.List;

@Data
public class ProductsActionDto {
    private List<Long> ids;
    private ProductAction action;
}
