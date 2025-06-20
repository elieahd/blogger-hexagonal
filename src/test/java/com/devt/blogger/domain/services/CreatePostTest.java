package com.devt.blogger.domain.services;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.exceptions.CategoryNotFoundByIdException;
import com.devt.blogger.domain.inbound.CreatePost;
import com.devt.blogger.domain.outbound.CategoryInventory;
import com.devt.blogger.domain.outbound.PostInventory;
import com.devt.blogger.domain.stubs.CategoryInMemoryInventory;
import com.devt.blogger.domain.stubs.PostInMemoryInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.devt.blogger.utils.TestObject.aCategory;
import static com.devt.randomizer.RandomizerUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.within;

class CreatePostTest {

    private CategoryInventory categoryInventory;
    private CreatePost createPost;

    @BeforeEach
    void setup() {
        categoryInventory = new CategoryInMemoryInventory();
        PostInventory postInventory = new PostInMemoryInventory();
        createPost = new PostService(postInventory, categoryInventory);
    }

    @Test
    void shouldThrowCategoryNotFoundByIdExceptionWhenCreating() {
        // Arrange
        String title = random(String.class);
        String content = random(String.class);
        UUID categoryId = UUID.randomUUID();
        // Act
        Throwable thrown = catchThrowable(() -> createPost.create(title, content, categoryId));
        // Assert
        assertThat(thrown).isInstanceOf(CategoryNotFoundByIdException.class)
                .hasMessage("Category with id " + categoryId + " not found");
    }

    @Test
    void shouldReturnCreatedPost() throws CategoryNotFoundByIdException {
        // Arrange
        String title = random(String.class);
        String content = random(String.class);
        Category category = aCategory();
        categoryInventory.create(category);
        // Act
        Post post = createPost.create(title, content, category.getId());
        // Assert
        assertThat(post.getId()).isNotNull();
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getCategory()).isEqualTo(category);
        assertThat(post.getCreatedDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }
}
