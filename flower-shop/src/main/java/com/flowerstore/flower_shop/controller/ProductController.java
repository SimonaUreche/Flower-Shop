package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.ProductCreationDTO;
import com.flowerstore.flower_shop.dto.ProductDTO;
import com.flowerstore.flower_shop.mapper.ProductMapper;
import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.service.ICategoryService;
import com.flowerstore.flower_shop.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    private final IProductService iProductService;
    private final ICategoryService categoryService;

    public ProductController(IProductService iProductService, ICategoryService categoryService) {
        this.iProductService = iProductService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Fetch all products", description = "This method retrieves all the products from the database.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Successfully fetched all products.") })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        List<Product> products = iProductService.getAllProducts();
        return ResponseEntity.ok(ProductMapper.toDtoList(products));
    }

    @Operation(summary = "Create a new product", description = "This method allows adding a new product to the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully added a new product."),
            @ApiResponse(responseCode = "400", description = "Invalid product data.")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> saveNewProduct(@RequestBody ProductCreationDTO productDTO) {
        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        Product saved = iProductService.addProduct(ProductMapper.toCreationEntity(productDTO, category));
        return ResponseEntity.ok(ProductMapper.toDto(saved));
    }

    @Operation(summary = "Get product by ID", description = "This method retrieves a product based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            Product product = iProductService.getProductById(id);
            return ResponseEntity.ok(ProductMapper.toDto(product));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
    }

    @Operation(summary = "Update an existing product", description = "This method updates the details of an existing product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the product."),
            @ApiResponse(responseCode = "400", description = "Invalid product data.")
    })
    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductCreationDTO dto) {
        Category category = categoryService.getCategoryById(dto.getCategoryId());
        Product updated = iProductService.updateProduct(ProductMapper.toCreationEntity(dto, category));
        return ResponseEntity.ok(ProductMapper.toDto(updated));
    }
    @Operation(summary = "Delete a product", description = "This method deletes a product by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        iProductService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted.");
    }
}
