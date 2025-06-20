package com.devt.blogger.domain.outbound;

import com.devt.blogger.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryInventory {

    List<Category> findAll();

    Category findById(UUID categoryId);

    void create(Category category);

}
