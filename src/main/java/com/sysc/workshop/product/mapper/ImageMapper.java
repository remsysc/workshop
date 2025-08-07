package com.sysc.workshop.product.mapper;

import com.sysc.workshop.product.dto.image.ImageDto;
import com.sysc.workshop.product.model.Image;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);
    List<ImageDto> toDtoList(List<Image> image);
}
