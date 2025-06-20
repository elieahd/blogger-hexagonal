package com.devt.blogger.infrastructure.inbound.rest;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.inbound.SearchCategories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryRest {

    private final SearchCategories searchCategories;

    public CategoryRest(SearchCategories searchCategories) {
        this.searchCategories = searchCategories;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = searchCategories.getAll();
        return ok(categories);
    }
}
