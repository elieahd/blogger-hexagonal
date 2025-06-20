package com.devt.blogger.domain.stubs;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.outbound.CategoryInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryInMemoryInventory implements CategoryInventory {

    private final List<Category> categories;

    public CategoryInMemoryInventory() {
        this.categories = new ArrayList<>();
    }

    @Override
    public List<Category> findAll() {
        return categories;
    }

    @Override
    public Category findById(UUID categoryId) {
        return categories.stream()
                .filter(c -> c.getId().equals(categoryId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(Category category) {
        if (category.getId() == null) {
            category.setId(UUID.randomUUID());
        }
        categories.add(category);
    }
}
