package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.repository.ProductRepository;
import com.flowerstore.flower_shop.service.IProductService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Product user = productRepository.findById(id);
        if(user != null) {
            return user;
        }
        throw new NoSuchElementException("Product with id" +  id + " does not exist");
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
