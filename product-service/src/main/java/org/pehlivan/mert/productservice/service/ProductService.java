package org.pehlivan.mert.productservice.service;


import org.pehlivan.mert.productservice.dto.request.CreateProductRequestDTO;
import org.pehlivan.mert.productservice.dto.request.UpdateProductRequestDTO;
import org.pehlivan.mert.productservice.dto.response.ResponseProductDTO;

import java.util.List;

public interface ProductService {
    ResponseProductDTO createProduct(CreateProductRequestDTO request);
    ResponseProductDTO updateProduct(UpdateProductRequestDTO request);
    void deleteProduct(Long id);
    ResponseProductDTO getProductById(Long id);
    List<ResponseProductDTO> getAllProducts();
} 