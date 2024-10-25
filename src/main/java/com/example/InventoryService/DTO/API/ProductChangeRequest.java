package com.example.InventoryService.DTO.API;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductChangeRequest implements Serializable {
    private Long productId;
    private int quantityChange;
}
