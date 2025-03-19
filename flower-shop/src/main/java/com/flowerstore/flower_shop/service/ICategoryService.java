package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.model.Order;

import java.util.List;

public interface ICategoryService
{
    Category addCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoriesById(Long id);
    Category updateCategieries(Category category);
    void deleteCategories(Long id);
}
