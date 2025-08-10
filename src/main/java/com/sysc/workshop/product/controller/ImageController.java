package com.sysc.workshop.product.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.dto.image.ImageDto;
import com.sysc.workshop.product.exception.ImageNotFoundException;
import com.sysc.workshop.product.model.Image;
import com.sysc.workshop.product.service.image.IImageService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService iImageService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<ImageDto>>> uploadImages(
        @RequestParam UUID productId,
        @RequestBody List<MultipartFile> files
    ) {
        try {
            List<ImageDto> imageDtos = iImageService.saveImages(
                files,
                productId
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Upload success!", imageDtos)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/{imageId}/download")
    public ResponseEntity<Resource> downloadImage(@PathVariable UUID imageId) {
        Image img = iImageService.getImageEntityById(imageId);
        ByteArrayResource resource = new ByteArrayResource(img.getImageData());

        if (img == null || resource == null) {
            throw new ResponseStatusException(NOT_FOUND, "Image Not Found");
        }

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.parseMediaType(img.getFileType()))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + img.getFileName() + "\""
            )
            .body(resource);
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse<ImageDto>> updateImage(
        @PathVariable UUID imageId,
        @RequestBody MultipartFile file
    ) {
        try {
            ImageDto image = iImageService.getImageById(imageId);
            iImageService.updateImage(file, imageId);
            return ResponseEntity.status(OK).body(
                ApiResponse.success("Update success!", image)
            );
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                ApiResponse.error(e.getMessage())
            );
        }
        // return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse<ImageDto>> deleteImage(
        @PathVariable UUID imageId
    ) {
        try {
            iImageService.deleteImageById(imageId);
            return ResponseEntity.status(OK).body(
                ApiResponse.success("Delete success!", null)
            );
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                ApiResponse.error(e.getMessage())
            );
        }
        //  return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ApiResponse<ImageDto>> getImageById(
        @PathVariable UUID imageId
    ) {
        ImageDto img = iImageService.getImageById(imageId);
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Success", img)
        );
    }
}
