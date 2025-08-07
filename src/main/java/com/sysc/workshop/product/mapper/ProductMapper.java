package com.sysc.workshop.product.mapper;

import com.sysc.workshop.product.dto.product.ProductDto;
import com.sysc.workshop.product.model.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ImageMapper.class })
public interface ProductMapper {
    @Mapping(source = "category.name", target = "categoryName")
    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> products);
}
