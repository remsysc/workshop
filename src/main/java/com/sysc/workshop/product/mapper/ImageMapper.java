package com.sysc.workshop.product.mapper;

import com.sysc.workshop.product.dto.ImageDto;
import com.sysc.workshop.product.model.Image;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);
    List<ImageDto> toDtoList(List<Image> image);
}
