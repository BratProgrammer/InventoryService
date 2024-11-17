package com.example.InventoryService.Services;

import com.example.InventoryService.DTO.API.ProductChangeRequest;
import com.example.InventoryService.DTO.API.ProductQuantityDto;
import com.example.InventoryService.Entities.ProductQuantity;
import com.example.InventoryService.Repositories.ProductQuantityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductQuantityService {

    private final ProductQuantityRepository productQuantityRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Page<ProductQuantity> getList(Pageable pageable) {
        return productQuantityRepository.findAll(pageable);
    }

    @Cacheable(value = "quantity", key = "#id")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ProductQuantity findById(Long id) {
        Optional<ProductQuantity> optional = productQuantityRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ProductQuantity> getMany(Collection<Long> ids) {
        return productQuantityRepository.findAllById(ids);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Cacheable(value = "quantity", key = "#productQuantity.id")
    public ProductQuantity create(ProductQuantity productQuantity) {
        return productQuantityRepository.save(productQuantity);
    }

    @Cacheable(value = "quantity", key = "#productQuantity.id")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ProductQuantity doOperation(ProductQuantity productQuantity, ProductChangeRequest changeRequest) {
        productQuantity.setQuantity(productQuantity.getQuantity() + changeRequest.getQuantityChange());
        return productQuantityRepository.save(productQuantity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(Long id) {
        productQuantityRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<ProductQuantity> getAll() {
        return productQuantityRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(ProductQuantity productQuantity) {
        productQuantityRepository.delete(productQuantity);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteMany(Collection<Long> ids) {
        productQuantityRepository.deleteAllById(ids);
    }
}
