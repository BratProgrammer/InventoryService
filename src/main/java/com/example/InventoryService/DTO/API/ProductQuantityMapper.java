package com.example.InventoryService.DTO.API;

import com.example.InventoryService.Entities.ProductQuantity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductQuantityMapper {
    ProductQuantity toEntity(ProductQuantityDto productQuantityDto);

    ProductQuantityDto toDto(ProductQuantity productQuantity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductQuantity partialUpdate(ProductQuantityDto productQuantityDto, @MappingTarget ProductQuantity productQuantity);

    ProductQuantity updateWithNull(ProductQuantityDto productQuantityDto, @MappingTarget ProductQuantity productQuantity);
}