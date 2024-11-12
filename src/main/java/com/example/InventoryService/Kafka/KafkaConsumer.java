package com.example.InventoryService.Kafka;

import com.example.InventoryService.DTO.Kafka.ProductActionDto;
import com.example.InventoryService.DTO.Kafka.ProductsActionDto;
import com.example.InventoryService.Entities.ProductQuantity;
import com.example.InventoryService.Services.ProductQuantityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.InventoryService.Enums.Action.CREATE;
import static com.example.InventoryService.Enums.Action.DELETE;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductQuantityService productQuantityService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "product_updated", groupId = "1")
    private void getProductAction(String message) throws JsonProcessingException {

        ProductActionDto productActionDto = objectMapper.readValue(message, ProductActionDto.class);

        if (productActionDto.getAction() == CREATE) {
            ProductQuantity productQuantity = new ProductQuantity(productActionDto.getId(), 0);
            productQuantityService.create(productQuantity);
        } else if (productActionDto.getAction() == DELETE) {
            productQuantityService.delete(productActionDto.getId());
        }
    }

    @KafkaListener(topics = "products_list_updated", groupId = "2")
    private void getProductsAction(String message) throws JsonProcessingException {

        ProductsActionDto productsActionDto = objectMapper.readValue(message, ProductsActionDto.class);

        if (productsActionDto.getAction() == CREATE) {
            for (Long id : productsActionDto.getIds()) {
                ProductQuantity productQuantity = new ProductQuantity(id, 0);
                productQuantityService.create(productQuantity);
            }
        } else if (productsActionDto.getAction() == DELETE) {
            for (Long id : productsActionDto.getIds()) {
                productQuantityService.delete(id);
            }
        }

    }

}
