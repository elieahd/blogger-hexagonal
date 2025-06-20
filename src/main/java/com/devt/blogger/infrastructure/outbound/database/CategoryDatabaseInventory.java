package com.devt.blogger.infrastructure.outbound.database;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.outbound.CategoryInventory;
import com.devt.blogger.infrastructure.outbound.OutboundAdapter;
import com.devt.blogger.infrastructure.outbound.database.dao.CategoryDao;

import java.util.List;
import java.util.UUID;

@OutboundAdapter
public class CategoryDatabaseInventory implements CategoryInventory {

    private final CategoryDao dao;

    public CategoryDatabaseInventory(CategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }

    @Override
    public Category findById(UUID categoryId) {
        return dao.findById(categoryId);
    }

    @Override
    public void create(Category category) {
        dao.create(category);
    }
}
