package com.sysc.workshop.product.repository;

import com.sysc.workshop.product.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findByProduct_id(UUID id);
}