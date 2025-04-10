package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.constants.UserType;
import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.flowerstore.flower_shop.model.Category;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Category category = new Category(2L, "Flori");
        testProduct = new Product(1L, "Trandafir", 15.0, "Trandafir roșu superb", "trandafir.jpg", category, 10);
    }
    @Test
    void testAddProduct() {
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        Product savedProduct = productService.addProduct(testProduct);

        assertNotNull(savedProduct);
        assertEquals("Trandafir", savedProduct.getName());
        assertEquals(15.0, savedProduct.getPrice());
        assertEquals("Trandafir roșu superb", savedProduct.getDescription());
        assertEquals("trandafir.jpg", savedProduct.getImage());
        assertEquals("Flori", savedProduct.getCategory());
        assertEquals(10, savedProduct.getStock());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testGetAllProducts() {
        Category category = new Category(2L, "Flori");
        List<Product> productList = Arrays.asList(
                testProduct,
                new Product(2L, "Lalea", 12.0, "Lalea galbenă", "lalea.jpg", category, 5)
        );
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Trandafir", result.get(0).getName());
        assertEquals("Lalea", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(testProduct);

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Trandafir", foundProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(null);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Product with id1 does not exist", exception.getMessage());
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.updateProduct(testProduct)).thenReturn(testProduct);

        Product updatedProduct = productService.updateProduct(testProduct);

        assertNotNull(updatedProduct);
        assertEquals("Trandafir", updatedProduct.getName());
        verify(productRepository, times(1)).updateProduct(testProduct);
    }

    @Test
    void testDeleteProduct() {
        doAnswer(invocation -> {
            return null;
        }).when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

}
