package com.example.demoAOP_Logging.mapper;

import com.example.demoAOP_Logging.dto.response.ProductResponseDTO;
import com.example.demoAOP_Logging.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toResponse(Product product);
}
