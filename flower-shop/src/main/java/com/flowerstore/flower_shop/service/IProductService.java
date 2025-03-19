package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
}
