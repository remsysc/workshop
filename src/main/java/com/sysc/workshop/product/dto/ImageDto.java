package com.sysc.workshop.product.dto;

import lombok.Data;

import java.util.UUID;

@Data
//carries only what the client needs
public class ImageDto {
    private UUID id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
}
