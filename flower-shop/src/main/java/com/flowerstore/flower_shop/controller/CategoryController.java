package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.CategoryDTO;
import com.flowerstore.flower_shop.mapper.CategoryMapper;
import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        List<Category> categories = iCategoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTOs);
    }

    @Operation(summary = "Add a new category", description = "This method adds a new category to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category added successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> saveNewCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category savedCategory = iCategoryService.addCategory(category);
        CategoryDTO savedCategoryDTO = CategoryMapper.toDto(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryDTO);
    }

    @Operation(summary = "Fetch category by ID", description = "This method returns the category for the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found and returned."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@Parameter(description = "ID of the category to fetch", required = true)
                                                   @PathVariable Long id) {
        try {
            Category category = iCategoryService.getCategoryById(id);
            CategoryDTO categoryDTO = CategoryMapper.toDto(category);
            return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Update an existing category", description = "This method allows you to update an existing category in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PutMapping
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = CategoryMapper.toEntity(categoryDTO);
            Category updatedCategory = iCategoryService.updateCategory(category);
            CategoryDTO updatedCategoryDTO = CategoryMapper.toDto(updatedCategory);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategoryDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Delete a category by ID", description = "This method deletes the category with the specified ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            iCategoryService.getCategoryById(id);
            iCategoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
        }
    }

}
