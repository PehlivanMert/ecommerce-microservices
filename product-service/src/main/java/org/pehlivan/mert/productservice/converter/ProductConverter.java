package org.pehlivan.mert.productservice.converter;


import org.pehlivan.mert.productservice.dto.response.ResponseProductDTO;
import org.pehlivan.mert.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ResponseProductDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ResponseProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription());
    }
} 