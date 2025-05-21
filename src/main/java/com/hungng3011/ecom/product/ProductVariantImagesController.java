package com.hungng3011.ecom.product;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products/{productId}/variants/{variantId}/images")
public class ProductVariantImagesController {
    private final ProductVariantImagesService imagesService;

    @Autowired
    public ProductVariantImagesController(ProductVariantImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all images for a product variant")
    @ApiResponse(responseCode = "404", description = "Variant not found or has no images")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ProductVariantImages>> getAllImagesByVariantId(
            @PathVariable Long productId, @PathVariable Long variantId) {
        List<ProductVariantImages> images = imagesService.getAllImagesByVariantId(variantId);
        if (images.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{imageId}")
    @ApiResponse(responseCode = "200", description = "Get image by ID")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductVariantImages> getImageById(
            @PathVariable Long productId, @PathVariable Long variantId, @PathVariable Long imageId) {
        return imagesService.getImageById(imageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "Image added")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Variant not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductVariantImages> addImage(
            @PathVariable Long productId, @PathVariable Long variantId, @RequestBody ProductVariantImages image) {
        ProductVariantImages added = imagesService.addImage(variantId, image);
        if (added == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(added);
    }

    @PutMapping("/{imageId}")
    @ApiResponse(responseCode = "200", description = "Image updated")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductVariantImages> updateImage(
            @PathVariable Long productId, @PathVariable Long variantId,
            @PathVariable Long imageId, @RequestBody ProductVariantImages image) {
        ProductVariantImages updated = imagesService.updateImage(imageId, image);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{imageId}")
    @ApiResponse(responseCode = "204", description = "Image deleted")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long productId, @PathVariable Long variantId, @PathVariable Long imageId) {
        boolean deleted = imagesService.deleteImage(imageId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
