package com.devt.blogger.repositories;

import com.devt.blogger.models.Category;
import com.devt.blogger.utils.IntegrationTest;
import com.devt.blogger.utils.TestObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static com.devt.blogger.utils.TestObject.aCategory;
import static com.devt.blogger.utils.TestObject.listOf;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryRepositoryTest extends IntegrationTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    void shouldReturnNullWhenCategoryDoesNotExistById() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        Category category = repository.findById(id);
        // Assert
        assertThat(category).isNull();
    }

    @Test
    void shouldReturnCategoryById() {
        // Arrange
        Category expectedCategory = aCategory();
        repository.create(expectedCategory);
        // Act
        Category category = repository.findById(expectedCategory.getId());
        // Assert
        assertThat(category.getId()).isEqualTo(expectedCategory.getId());
        assertThat(category.getName()).isEqualTo(expectedCategory.getName());
    }

    @Test
    @Sql(scripts = {"/sql/clean.sql"})
    void shouldReturnAllCategories() {
        // Arrange
        List<Category> expectedCategories = listOf(TestObject::aCategory);
        expectedCategories.forEach(repository::create);
        // Act
        List<Category> categories = repository.findAll();
        // Assert
        assertThat(categories)
                .hasSameSizeAs(expectedCategories)
                .allSatisfy(category -> {
                    Category expected = expectedCategories.stream()
                            .filter(c -> c.getId().equals(category.getId()))
                            .findFirst()
                            .orElseThrow();
                    assertThat(category.getName()).isEqualTo(expected.getName());
                });
    }

}
