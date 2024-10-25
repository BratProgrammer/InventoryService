package com.example.InventoryService.Services;

import com.example.InventoryService.DTO.API.ProductChangeRequest;
import com.example.InventoryService.DTO.API.ProductQuantityDto;
import com.example.InventoryService.Entities.ProductQuantity;
import com.example.InventoryService.Repositories.ProductQuantityRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductQuantityService {

    private final ProductQuantityRepository productQuantityRepository;

    public Page<ProductQuantity> getList(Pageable pageable) {
        return productQuantityRepository.findAll(pageable);
    }

    public ProductQuantity findById(Long id) {
        Optional<ProductQuantity> optional = productQuantityRepository.findById(id);
        return optional.orElse(null);
    }

    public List<ProductQuantity> getMany(Collection<Long> ids) {
        return productQuantityRepository.findAllById(ids);
    }

    public ProductQuantity create(ProductQuantity productQuantity) {
        return productQuantityRepository.save(productQuantity);
    }

    public ProductQuantity patch(ProductQuantity productQuantity) {
        return productQuantityRepository.save(productQuantity);
    }

    public boolean isItValidRewrite(ProductQuantityDto dto) {
        return dto.getQuantity() >= 0;
    }

    public boolean isItValidOperation(ProductChangeRequest changeRequest, ProductQuantity productQuantity) {
        return productQuantity.getQuantity() + changeRequest.getQuantityChange() >= 0;
    }

    public List<ProductQuantity> patchMany(Collection<ProductQuantity> productQuantities) {
        return productQuantityRepository.saveAll(productQuantities);
    }

    public ProductQuantity doOperation(ProductQuantity productQuantity, ProductChangeRequest changeRequest) {
        productQuantity.setQuantity(productQuantity.getQuantity() + changeRequest.getQuantityChange());
        return productQuantityRepository.save(productQuantity);
    }

    public void delete(Long id) {
        productQuantityRepository.deleteById(id);
    }
    public void delete(ProductQuantity productQuantity) {
        productQuantityRepository.delete(productQuantity);
    }

    public void deleteMany(Collection<Long> ids) {
        productQuantityRepository.deleteAllById(ids);
    }
}
