package com.example.InventoryService.DTO.Kafka;

import com.example.InventoryService.Enums.Action;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductActionDto {
    private Long id;
    private Action action;
}
