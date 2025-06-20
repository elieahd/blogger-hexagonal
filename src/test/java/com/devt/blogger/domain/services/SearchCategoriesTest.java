package com.devt.blogger.domain.services;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.inbound.SearchCategories;
import com.devt.blogger.domain.outbound.CategoryInventory;
import com.devt.blogger.domain.stubs.CategoryInMemoryInventory;
import com.devt.blogger.utils.TestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devt.blogger.utils.TestObject.listOf;
import static org.assertj.core.api.Assertions.assertThat;

class SearchCategoriesTest {

    private CategoryInventory categoryInventory;
    private SearchCategories searchCategories;

    @BeforeEach
    void setup() {
        categoryInventory = new CategoryInMemoryInventory();
        searchCategories = new CategoryService(categoryInventory);
    }

    @Test
    void shouldReturnCategories() {
        // Arrange
        List<Category> expectedCategories = listOf(TestObject::aCategory);
        expectedCategories.forEach(categoryInventory::create);
        // Act
        List<Category> categories = searchCategories.getAll();
        // Assert
        assertThat(categories).isEqualTo(expectedCategories);
    }


}
