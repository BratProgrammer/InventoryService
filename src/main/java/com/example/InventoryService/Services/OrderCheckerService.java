package com.example.InventoryService.Services;

import com.example.InventoryService.DTO.Kafka.OrderRequest;
import com.example.InventoryService.DTO.Kafka.OrderRequestItem;
import com.example.InventoryService.Entities.ProductQuantity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCheckerService {

    private final ProductQuantityService productQuantityService;

    @Transactional
    public boolean isOrderPossible(OrderRequest orderRequest) {
        List<ProductQuantity> quantities = productQuantityService.getMany(orderRequest.getOrderRequestItems().stream().map(OrderRequestItem::getProductId).toList());

        for (int i = 0; i < quantities.size(); i++) {
            if (orderRequest.getOrderRequestItems().get(i).getRequestedQuantity() > quantities.get(i).getQuantity()) {
                return false;
            }
        }
        return true;
    }

}