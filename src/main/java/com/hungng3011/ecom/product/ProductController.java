package com.hungng3011.ecom.product;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "Get all products")
    @ApiResponse(responseCode = "404", description = "Products not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public String getAllProducts() {
        return productService.getAllProducts().toString();
    }

    @PostMapping("/")
    @ApiResponse(responseCode = "200", description = "Product added")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    public String addProduct() {
        return "Product added";
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Get product by ID")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public String getProductById(String id) {
        return "Product details: " + id;
    }

    @PostMapping("/{id}")
    public String updateProduct(String id) {
        return "Product updated: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(String id) {
        return "Product deleted: " + id;
    }
}
