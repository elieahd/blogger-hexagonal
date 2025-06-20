package com.devt.blogger.domain.services;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.inbound.SearchCategories;
import com.devt.blogger.domain.outbound.CategoryInventory;

import java.util.List;

@DomainService
public class CategoryService implements SearchCategories {

    private final CategoryInventory categoryInventory;

    public CategoryService(CategoryInventory categoryInventory) {
        this.categoryInventory = categoryInventory;
    }

    @Override
    public List<Category> getAll() {
        return categoryInventory.findAll();
    }
}
