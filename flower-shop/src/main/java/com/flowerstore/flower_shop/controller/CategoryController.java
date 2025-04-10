package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final ICategoryService iCategoryService;

    public CategoryController(ICategoryService iCategoryService) {
        this.iCategoryService = iCategoryService;
    }

    @Operation(summary = "Fetch all categories", description = "This method returns all categories from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully returned all categories.")
    @GetMapping
    public ResponseEntity findAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(iCategoryService.getAllCategories());
    }

    @Operation(summary = "Add a new category", description = "This method adds a new category to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category added successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PostMapping
    public ResponseEntity saveNewCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(iCategoryService.addCategory(category));
    }

    @Operation(summary = "Fetch category by ID", description = "This method returns the category for the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found and returned."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity getCategory(@Parameter(description = "ID of the category to fetch", required = true)
                                      @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iCategoryService.getCategoriesById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Update an existing category", description = "This method allows you to update an existing category in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PutMapping
    public ResponseEntity updateCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(iCategoryService.updateCategieries(category));
    }

    @Operation(summary = "Delete a category by ID", description = "This method deletes the category with the specified ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@Parameter(description = "ID of the category to delete", required = true)
                                         @PathVariable Long id) {
        iCategoryService.deleteCategories(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successful operation");
    }
}
