package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository
{
    private final List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    public Category save(Category category) {
        if(category.getId() == null) {
            category.setId(nextId++);
        }
        categories.add(category);
        return category;
    }

    public List<Category> findAll() {
        return categories;
    }

    public Category findById(Long id) {
        return categories.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public Category updateCategory(Category category) {
        Category oldCategory = findById(category.getId());

        if(oldCategory != null){
            oldCategory.setName(category.getName());
            return oldCategory;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return categories.removeIf(product -> product.getId().equals(id));
    }
}
