package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.repository.CategoryRepository;
import com.flowerstore.flower_shop.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoriesById(Long id) {
        Category category = categoryRepository.findById(id);
        if (category != null) {
            return category;
        }
        throw new RuntimeException("Category with id " + id + " not found");
    }

    @Override
    public Category updateCategieries(Category category) {
        return categoryRepository.updateCategory(category);
    }

    @Override
    public void deleteCategories(Long id) {
        categoryRepository.deleteById(id);
    }
}
