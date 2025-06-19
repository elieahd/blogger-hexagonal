package com.devt.blogger.controllers;

import com.devt.blogger.models.Category;
import com.devt.blogger.repositories.CategoryRepository;
import com.devt.blogger.utils.TestObject;
import com.devt.blogger.utils.rest.RestIntegrationTest;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.devt.blogger.utils.TestObject.listOf;
import static com.devt.blogger.utils.rest.RestAssuredHelper.get;
import static com.devt.blogger.utils.rest.ValidatableResponseAssert.assertThat;

class CategoryControllerTest extends RestIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    public static void assertCategory(Category actual, Category expected) {
        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    @Sql("/sql/clean.sql")
    void shouldReturnAllCategories() {
        // Arrange
        List<Category> categories = listOf(TestObject::aCategory, 5);
        categories.forEach(categoryRepository::create);
        // Act
        ValidatableResponse response = get("api/v1/categories");
        // Assert
        assertThat(response)
                .hasStatusCode(200)
                .hasArraySize(5)
                .satisfiesInArray(0, Category.class, category -> assertCategory(category, categories.getFirst()))
                .satisfiesInArray(1, Category.class, category -> assertCategory(category, categories.get(1)))
                .satisfiesInArray(2, Category.class, category -> assertCategory(category, categories.get(2)))
                .satisfiesInArray(3, Category.class, category -> assertCategory(category, categories.get(3)))
                .satisfiesInArray(4, Category.class, category -> assertCategory(category, categories.get(4)));
    }

}
