package com.example.InventoryService.DTO.API;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.InventoryService.Entities.ProductQuantity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDto implements Serializable {
    Long id;
    @Min(0)
    int quantity;
}