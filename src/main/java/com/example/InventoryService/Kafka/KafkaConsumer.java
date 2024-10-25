package com.example.InventoryService.Kafka;

import com.example.InventoryService.DTO.Kafka.ProductActionDto;
import com.example.InventoryService.DTO.Kafka.ProductsActionDto;
import com.example.InventoryService.Entities.ProductQuantity;
import com.example.InventoryService.Services.ProductQuantityService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.InventoryService.Enums.ProductAction.CREATE;
import static com.example.InventoryService.Enums.ProductAction.DELETE;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductQuantityService productQuantityService;


    @KafkaListener(topics = "product_updated", groupId = "1")
    private void getProductAction(ProductActionDto actionDto) {

        if (actionDto.getAction() == CREATE) {
            ProductQuantity productQuantity = new ProductQuantity(actionDto.getId(), 0);
            productQuantityService.create(productQuantity);
        } else if (actionDto.getAction() == DELETE) {
            productQuantityService.delete(actionDto.getId());
        }

    }

    @KafkaListener(topics = "products_list_updated", groupId = "1")
    private void getProductsAction(ProductsActionDto actionDto) {

        if (actionDto.getAction() == CREATE) {
            for (Long id : actionDto.getIds()) {
                ProductQuantity productQuantity = new ProductQuantity(id, 0);
                productQuantityService.create(productQuantity);
            }
        } else if (actionDto.getAction() == DELETE) {
            for (Long id : actionDto.getIds()) {
                productQuantityService.delete(id);
            }
        }

    }

}
