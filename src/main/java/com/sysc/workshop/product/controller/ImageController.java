package com.sysc.workshop.product.controller;

import com.sysc.workshop.product.dto.ImageDto;
import com.sysc.workshop.product.exception.ImageNotFoundException;
import com.sysc.workshop.product.model.Image;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService iImageService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam UUID productId) {
        try {
            List<ImageDto> imageDtos = iImageService.saveImages(files, productId);

            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable UUID imageId) {
        Image img = iImageService.getImageEntityById(imageId);
        ByteArrayResource resource = new ByteArrayResource(img.getImageData());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(img.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + img.getFileName() + "\"")
                .body(resource);
    }
    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable UUID imageId, @RequestBody MultipartFile file) {
        try {
            Image image = iImageService.getImageEntityById(imageId);

            iImageService.updateImage(file, imageId);
            return ResponseEntity.ok(new ApiResponse("Update success!", image));

        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
       // return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable UUID imageId) {
        try {
            Image image = iImageService.getImageEntityById(imageId);

                iImageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));

        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
      //  return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ApiResponse> getImageById(@PathVariable UUID imageId) {

        ImageDto img = iImageService.getImageById(imageId);
        return ResponseEntity.ok(new ApiResponse("Success", img));
    }
}
