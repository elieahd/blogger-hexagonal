package com.devt.blogger.services;

import com.devt.blogger.models.Category;
import com.devt.blogger.repositories.CategoryRepository;
import com.devt.blogger.utils.TestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devt.blogger.utils.TestObject.listOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        categoryRepository = mock();
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void shouldReturnCategories() {
        // Arrange
        List<Category> expectedCategories = listOf(TestObject::aCategory);
        when(categoryRepository.findAll()).thenReturn(expectedCategories);
        // Act
        List<Category> categories = categoryService.getCategories();
        // Assert
        assertThat(categories).isEqualTo(expectedCategories);
    }

}
