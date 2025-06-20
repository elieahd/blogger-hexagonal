package com.devt.blogger.domain.services;

import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.exceptions.PostNotFoundByIdException;
import com.devt.blogger.domain.inbound.SearchPosts;
import com.devt.blogger.domain.outbound.CategoryInventory;
import com.devt.blogger.domain.outbound.PostInventory;
import com.devt.blogger.domain.stubs.CategoryInMemoryInventory;
import com.devt.blogger.domain.stubs.PostInMemoryInventory;
import com.devt.blogger.utils.TestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.devt.blogger.utils.TestObject.aPost;
import static com.devt.blogger.utils.TestObject.listOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SearchPostsTest {

    private PostInventory postInventory;
    private SearchPosts searchPosts;

    @BeforeEach
    void setup() {
        CategoryInventory categoryInventory = new CategoryInMemoryInventory();
        postInventory = new PostInMemoryInventory();
        searchPosts = new PostService(postInventory, categoryInventory);
    }

    @Test
    void shouldReturnPosts() {
        // Arrange
        List<Post> expectedPosts = listOf(TestObject::aPost);
        expectedPosts.forEach(postInventory::create);
        // Act
        List<Post> posts = searchPosts.getAll();
        // Assert
        assertThat(posts).isEqualTo(expectedPosts);
    }

    @Test
    void shouldThrowPostNotFoundByIdException() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        Throwable thrown = catchThrowable(() -> searchPosts.getById(id));
        // Assert
        assertThat(thrown).isInstanceOf(PostNotFoundByIdException.class)
                .hasMessage("Post with id " + id + " not found");
    }

    @Test
    void shouldReturnPostById() throws PostNotFoundByIdException {
        // Arrange
        Post expectedPost = aPost();
        postInventory.create(expectedPost);
        // Act
        Post post = searchPosts.getById(expectedPost.getId());
        // Assert
        assertThat(post).isEqualTo(expectedPost);
    }
}
