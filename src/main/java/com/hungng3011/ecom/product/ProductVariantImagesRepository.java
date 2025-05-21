package com.hungng3011.ecom.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantImagesRepository extends JpaRepository<ProductVariantImages, Long> {
    List<ProductVariantImages> findByProductVariantId(Long productVariantId);
    void deleteByProductVariantId(Long productVariantId);
}
