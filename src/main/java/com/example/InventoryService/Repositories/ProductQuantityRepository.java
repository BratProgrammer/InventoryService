package com.example.InventoryService.Repositories;

import com.example.InventoryService.Entities.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {
}