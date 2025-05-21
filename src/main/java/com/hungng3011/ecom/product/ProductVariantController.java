package com.hungng3011.ecom.product;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products/{productId}/variants")
public class ProductVariantController {
    private final ProductVariantService variantService;

    @Autowired
    public ProductVariantController(ProductVariantService variantService) {
        this.variantService = variantService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all variants for a product")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ProductVariant>> getAllVariantsByProductId(@PathVariable Long productId) {
        List<ProductVariant> variants = variantService.getAllVariantsByProductId(productId);
        if (variants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(variants);
    }

    @GetMapping("/{variantId}")
    @ApiResponse(responseCode = "200", description = "Get variant by ID")
    @ApiResponse(responseCode = "404", description = "Variant not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductVariant> getVariantById(@PathVariable Long productId, @PathVariable Long variantId) {
        return variantService.getVariantById(variantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "Variant created")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductVariant> createVariant(@PathVariable Long productId, @RequestBody ProductVariant variant) {
        ProductVariant created = variantService.createVariant(productId, variant);
        if (created == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{variantId}")
    @ApiResponse(responseCode = "200", description = "Variant updated")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Variant not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductVariant> updateVariant(@PathVariable Long productId,
                                                      @PathVariable Long variantId,
                                                      @RequestBody ProductVariant variant) {
        ProductVariant updated = variantService.updateVariant(variantId, variant);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{variantId}")
    @ApiResponse(responseCode = "204", description = "Variant deleted")
    @ApiResponse(responseCode = "404", description = "Variant not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long productId, @PathVariable Long variantId) {
        boolean deleted = variantService.deleteVariant(variantId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
