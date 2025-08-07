package com.sysc.workshop.product.dto.image;

import java.util.UUID;
import lombok.Data;

@Data
//carries only what the client needs
public class ImageDto {

    private UUID id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
}
