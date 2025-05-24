package org.pehlivan.mert.orderservice.client;

import org.pehlivan.mert.orderservice.dto.ApiResponse;
import org.pehlivan.mert.orderservice.dto.request.ProductDTO;
import org.pehlivan.mert.orderservice.dto.request.UpdateProductRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "${product-service.url}")
public interface ProductServiceClient {
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<ProductDTO>> getProduct(@PathVariable Long id);

    @PutMapping
    ResponseEntity<ApiResponse<Void>> updateStock(@RequestBody UpdateProductRequestDTO request);
}
