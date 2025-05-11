package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .id(2L)
                .name("Flori")
                .build();

        testProduct = Product.builder()
                .id(1L)
                .name("Trandafir")
                .price(15.0)
                .description("Trandafir roșu superb")
                .image("trandafir.jpg")
                .category(testCategory)
                .stock(10)
                .build();
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
        assertEquals(testCategory, savedProduct.getCategory());
        assertEquals(10, savedProduct.getStock());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testGetAllProducts() {
        Product secondProduct = Product.builder()
                .id(2L)
                .name("Lalea")
                .price(12.0)
                .description("Lalea galbenă superba")
                .image("lalea.jpg")
                .category(testCategory)
                .stock(5)
                .build();
        List<Product> productList = Arrays.asList(testProduct, secondProduct);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Trandafir", result.get(0).getName());
        assertEquals("Lalea", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Trandafir", foundProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> productService.getProductById(1L));

        assertEquals("Product with id 1 not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProduct_Success() {
        when(productRepository.existsById(testProduct.getId())).thenReturn(true);
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        Product updatedProduct = productService.updateProduct(testProduct);

        assertNotNull(updatedProduct);
        assertEquals("Trandafir", updatedProduct.getName());
        verify(productRepository, times(1)).existsById(testProduct.getId());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.existsById(testProduct.getId())).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> productService.updateProduct(testProduct));

        assertEquals("Product not found for update", exception.getMessage());
        verify(productRepository, times(1)).existsById(testProduct.getId());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
