package org.pehlivan.mert.productservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pehlivan.mert.productservice.dto.ApiResponse;
import org.pehlivan.mert.productservice.dto.request.CreateProductRequestDTO;
import org.pehlivan.mert.productservice.dto.request.UpdateProductRequestDTO;
import org.pehlivan.mert.productservice.dto.response.ResponseProductDTO;
import org.pehlivan.mert.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResponseProductDTO>> createProduct(@Valid @RequestBody CreateProductRequestDTO request) {
        try {
            ResponseProductDTO response = productService.createProduct(request);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResponseProductDTO>> updateProduct(@Valid @RequestBody UpdateProductRequestDTO request) {
        try {
            ResponseProductDTO response = productService.updateProduct(request);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseProductDTO>> getProductById(@PathVariable Long id) {
        try {
            ResponseProductDTO response = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseProductDTO>>> getAllProducts() {
        try {
            List<ResponseProductDTO> response = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Products retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }
} 