package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {
    private final IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @Operation(
            summary = "Fetch all products",
            description = "This method retrieves all the products from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all products.")
    })
    @GetMapping
    public ResponseEntity findAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.getAllProducts());
    }

    @Operation(
            summary = "Create a new product",
            description = "This method allows adding a new product to the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a new product."),
            @ApiResponse(responseCode = "400", description = "Invalid product data.")
    })
    @PostMapping
    public ResponseEntity saveNewProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.addProduct(product));
    }

    @Operation(
            summary = "Get product by ID",
            description = "This method retrieves a product based on the provided ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity getProduct(@Parameter(description = "The ID of the product to fetch", required = true) @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iProductService.getProductById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Update an existing product",
            description = "This method updates the details of an existing product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product."),
            @ApiResponse(responseCode = "400", description = "Invalid product data.")
    })
    @PutMapping
    public ResponseEntity updateProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.updateProduct(product));
    }

    @Operation(
            summary = "Delete a product",
            description = "This method deletes a product by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@Parameter(description = "The ID of the product to delete", required = true) @PathVariable Long id) {
        iProductService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successful operation");
    }
}
