package com.sysc.workshop.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String fileName;

    @Column(nullable = false)
    private String fileType;



    @Column(name="data", columnDefinition = "BYTEA") // for Postgres
    @JsonIgnore                                      // don’t expose raw bytes in your product JSON
    private byte[] imageData; //also hide the data with dto

    @Column(nullable = true, unique = true)
    private String downloadUrl;

    @ManyToOne
    @JsonIgnore
    @JoinColumn (name = "product_id")
    Product product;
}
