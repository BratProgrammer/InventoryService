package com.example.InventoryService.Controllers;

import com.example.InventoryService.DTO.API.ProductChangeRequest;
import com.example.InventoryService.DTO.API.ProductQuantityDto;
import com.example.InventoryService.Entities.ProductQuantity;
import com.example.InventoryService.Services.ProductQuantityService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/rest/admin-ui/inventory")
@RequiredArgsConstructor
public class ProductQuantityController {

    private final ProductQuantityMapper productQuantityMapper;

    private final ProductQuantityService productQuantityService;

    @GetMapping(path = "/{id}")
    public ProductQuantityDto getProductQuantity(@PathVariable Long id) {

        ProductQuantity productQuantity = productQuantityService.findById(id);

        if (productQuantity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductQuantity with id `%s` not found".formatted(id));
        }

        return productQuantityMapper.toDto(productQuantity);
    }

    @PutMapping("/rewrite")
    public ProductQuantityDto rewriteQuantity(@RequestBody ProductQuantityDto quantityDto) {
        ProductQuantity productQuantity = productQuantityService.findById(quantityDto.getId());

        if (productQuantity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductQuantity with id `%s` not found".formatted(quantityDto.getId()));
        }

        if (productQuantityService.isItValidRewrite(quantityDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity can't be `%s`. It should be >= 0".formatted(quantityDto.getQuantity()));
        }

        return productQuantityMapper.toDto(productQuantityService.patch(productQuantityMapper.toEntity(quantityDto)));
    }

    @PatchMapping("/new-operation")
    public ProductQuantityDto doOperation(@RequestBody ProductChangeRequest changeRequest) {

        ProductQuantity productQuantity = productQuantityService.findById(changeRequest.getProductId());

        if (productQuantity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductQuantity with id `%s` not found".formatted(changeRequest.getProductId()));
        }

        if (productQuantityService.isItValidOperation(changeRequest, productQuantity)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity can't be `%s`. It should be >= 0".formatted(productQuantity.getQuantity() + changeRequest.getQuantityChange()));
        }

        return productQuantityMapper.toDto(productQuantityService.doOperation(productQuantity, changeRequest));
    }

}

