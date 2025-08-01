package com.sysc.workshop.product.service.image;

import com.sysc.workshop.product.dto.ImageDto;
import com.sysc.workshop.product.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IImageService {

    ImageDto getImageById(UUID id);

    Image getImageEntityById(UUID id);

    void deleteImageById(UUID id);
    List<ImageDto> saveImages(List<MultipartFile> files, UUID productId);
    void updateImage(MultipartFile file, UUID imageId);

}
