package com.example.demoAOP_Logging.controller;

import com.example.demoAOP_Logging.dto.request.ProductRequestDTO;
import com.example.demoAOP_Logging.dto.response.ApiResponse;
import com.example.demoAOP_Logging.dto.response.ProductResponseDTO;
import com.example.demoAOP_Logging.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Lấy ds sản phẩm
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getProducts() {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Xem danh sách sản phẩm",
                productService.getAll()
        ));
    }

    // Tìm sp theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Lấy sản phẩm id = " + id,
                productService.getById(Integer.parseInt(id))
        ));
    }

    // Thêm sản phẩm
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(@Valid @RequestBody ProductRequestDTO request) {
        return ResponseEntity.status(201).body(new ApiResponse(
                201,
                "Tạo sản phẩm thành công",
                productService.createProduct(request)
        ));
    }

    // Cập nhật sp
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Cập nhật sản phẩm thành công",
                productService.updateProduct(Integer.parseInt(id), request)
        ));
    }

    // Xoá sp
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(Integer.parseInt(id));
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Xoá sản phẩm thành công",
                null
        ));

    }
}
