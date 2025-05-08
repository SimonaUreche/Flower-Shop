package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Category;
import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
}
