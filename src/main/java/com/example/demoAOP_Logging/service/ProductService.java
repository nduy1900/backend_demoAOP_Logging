package com.example.demoAOP_Logging.service;

import com.example.demoAOP_Logging.dto.request.ProductRequestDTO;
import com.example.demoAOP_Logging.dto.response.ProductResponseDTO;
import com.example.demoAOP_Logging.entity.Product;
import com.example.demoAOP_Logging.exception.ResourceNotFoundException;
import com.example.demoAOP_Logging.mapper.ProductMapper;
import com.example.demoAOP_Logging.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    // Lấy ds sản phẩm
    public List<ProductResponseDTO> getAll() {
        logger.info("API được gọi: ProductController.getProducts()");
        logger.info("Bắt đầu lấy danh sách sản phẩm");
        List<Product> products = productRepository.findAll();
        logger.info("Lấy danh sách sản phẩm thành công, số lượng: {}",
                products.size());
        return products
                .stream()
                .map(productMapper::toResponse).toList();
    }

    // Lấy sp theo id
    public ProductResponseDTO getById(int id) {
        logger.info("API được gọi: ProductController.getProductById()");
        logger.info("Bắt đầu tìm sản phẩm với ID: {}", id);
        Product findProductById = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Không tìm thấy sản phẩm, ID: " + id);
                    return new ResourceNotFoundException("Không tìm thấy sản phẩm có id = " + id);
                });
        logger.info("Tìm thấy sản phẩm: {}", findProductById.getName());
        return productMapper.toResponse(findProductById);
    }

    // Tạo mới sản phẩm
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        logger.info("API được gọi: ProductController.createProduct()");
        logger.info("Bắt đầu thêm sản phẩm: " + request.getName());

        Product createProduct = new Product();
        createProduct.setName(request.getName());
        createProduct.setPrice(request.getPrice());
        createProduct.setQuantity(request.getQuantity());

        Product savedProduct = productRepository.save(createProduct);
        logger.info("Thêm sản phẩm thành công, ID: " + savedProduct.getId());
        return productMapper.toResponse(savedProduct);
    }

    // Cập nhật sp
    public ProductResponseDTO updateProduct(int id, ProductRequestDTO request) {
        logger.info("API được gọi: ProductController.updateProduct(), ID: " + id);
        Product updatedProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Không tìm thấy sản phẩm với ID: {}", id);
                    return new ResourceNotFoundException("Không tìm thấy sản phẩm có id = " + id);
                });

        updatedProduct.setName(request.getName());
        updatedProduct.setPrice(request.getPrice());
        updatedProduct.setQuantity(request.getQuantity());
        productRepository.save(updatedProduct);
        logger.info("Cập nhật sản phẩm thành công, ID: {}",
                updatedProduct.getId());
        return productMapper.toResponse(updatedProduct);
    }

    // Xoá sp
    public void deleteProduct(int id) {
        logger.info("API được gọi: ProductController.deleteProduct(), ID: " + id);
        Product deleteProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Không tìm thấy sản phẩm với ID: {}", id);
                    return new ResourceNotFoundException("Không tìm thấy sản phẩm có id = " + id);
                });
        productRepository.delete(deleteProduct);
        logger.info("Xóa sản phẩm thành công, ID: {}", id);
    }
}
