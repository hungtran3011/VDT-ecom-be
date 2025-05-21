package com.hungng3011.ecom.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductVariantService(ProductVariantRepository productVariantRepository,
                                ProductRepository productRepository) {
        this.productVariantRepository = productVariantRepository;
        this.productRepository = productRepository;
    }

    public List<ProductVariant> getAllVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    public Optional<ProductVariant> getVariantById(Long id) {
        return productVariantRepository.findById(id);
    }

    @Transactional
    public ProductVariant createVariant(Long productId, ProductVariant variant) {
        return productRepository.findById(productId).map(product -> {
            variant.setProduct(product);
            return productVariantRepository.save(variant);
        }).orElse(null);
    }

    @Transactional
    public ProductVariant updateVariant(Long id, ProductVariant updatedVariant) {
        return productVariantRepository.findById(id).map(variant -> {
            variant.setName(updatedVariant.getName());
            variant.setPrice(updatedVariant.getPrice());
            variant.setCurrency(updatedVariant.getCurrency());
            return productVariantRepository.save(variant);
        }).orElse(null);
    }

    @Transactional
    public boolean deleteVariant(Long id) {
        if (productVariantRepository.existsById(id)) {
            productVariantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
