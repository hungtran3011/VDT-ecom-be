package com.hungng3011.ecom.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantImagesService {
    private final ProductVariantImagesRepository imagesRepository;
    private final ProductVariantRepository variantRepository;

    @Autowired
    public ProductVariantImagesService(ProductVariantImagesRepository imagesRepository,
                                      ProductVariantRepository variantRepository) {
        this.imagesRepository = imagesRepository;
        this.variantRepository = variantRepository;
    }

    public List<ProductVariantImages> getAllImagesByVariantId(Long variantId) {
        return imagesRepository.findByProductVariantId(variantId);
    }

    public Optional<ProductVariantImages> getImageById(Long id) {
        return imagesRepository.findById(id);
    }

    @Transactional
    public ProductVariantImages addImage(Long variantId, ProductVariantImages image) {
        return variantRepository.findById(variantId).map(variant -> {
            image.setProductVariant(variant);
            return imagesRepository.save(image);
        }).orElse(null);
    }

    @Transactional
    public ProductVariantImages updateImage(Long id, ProductVariantImages updatedImage) {
        return imagesRepository.findById(id).map(image -> {
            image.setImageUrl(updatedImage.getImageUrl());
            return imagesRepository.save(image);
        }).orElse(null);
    }

    @Transactional
    public boolean deleteImage(Long id) {
        if (imagesRepository.existsById(id)) {
            imagesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
