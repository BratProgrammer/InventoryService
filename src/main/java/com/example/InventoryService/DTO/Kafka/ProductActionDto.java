package com.example.InventoryService.DTO.Kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductActionDto {
    private Long id;
    private String action;
}
