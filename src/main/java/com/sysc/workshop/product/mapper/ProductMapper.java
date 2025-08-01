package com.sysc.workshop.product.mapper;
import com.sysc.workshop.product.model.Product;
import com.sysc.workshop.product.dto.ProductDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    ProductDto toDto(Product product);
    List<ProductDto> toDtoList(List<Product> products);

}
