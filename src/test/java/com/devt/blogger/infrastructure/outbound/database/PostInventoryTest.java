package com.devt.blogger.infrastructure.outbound.database;

import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.domain.outbound.CategoryInventory;
import com.devt.blogger.domain.outbound.PostInventory;
import com.devt.blogger.utils.IntegrationTest;
import com.devt.blogger.utils.TestObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static com.devt.blogger.utils.TestObject.aPost;
import static com.devt.blogger.utils.TestObject.listOf;
import static org.assertj.core.api.Assertions.assertThat;

class PostInventoryTest extends IntegrationTest {

    @Autowired
    private PostInventory postInventory;

    @Autowired
    private CategoryInventory categoryInventory;

    @Test
    void shouldReturnNullWhenPostDoesNotExistById() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        Post post = postInventory.findById(id);
        // Assert
        assertThat(post).isNull();
    }

    @Test
    void shouldReturnPostById() {
        // Arrange
        Post existingPost = aPost();
        categoryInventory.create(existingPost.getCategory());
        postInventory.create(existingPost);
        // Act
        Post post = postInventory.findById(existingPost.getId());
        // Assert
        assertThat(post.getId()).isEqualTo(existingPost.getId());
        assertThat(post.getTitle()).isEqualTo(existingPost.getTitle());
        assertThat(post.getContent()).isEqualTo(existingPost.getContent());
        assertThat(post.getCategory().getId()).isEqualTo(existingPost.getCategory().getId());
        assertThat(post.getCategory().getName()).isEqualTo(existingPost.getCategory().getName());
        assertThat(post.getCreatedDate()).isEqualTo(existingPost.getCreatedDate());
    }

    @Test
    @Sql(scripts = {"/sql/clean.sql"})
    void shouldReturnAllPosts() {
        // Arrange
        List<Post> expectedPosts = listOf(TestObject::aPost);
        expectedPosts.forEach(post -> {
            categoryInventory.create(post.getCategory());
            postInventory.create(post);
        });
        // Act
        List<Post> posts = postInventory.findAll();
        // Assert
        assertThat(posts)
                .hasSameSizeAs(expectedPosts)
                .allSatisfy(post -> {
                    Post expected = expectedPosts.stream()
                            .filter(p -> p.getId().equals(post.getId()))
                            .findFirst()
                            .orElseThrow();
                    assertThat(post.getTitle()).isEqualTo(expected.getTitle());
                    assertThat(post.getContent()).isEqualTo(expected.getContent());
                    assertThat(post.getCreatedDate()).isEqualTo(expected.getCreatedDate());
                    assertThat(post.getCategory()).isNotNull();
                    assertThat(post.getCategory().getId()).isEqualTo(expected.getCategory().getId());
                    assertThat(post.getCategory().getName()).isEqualTo(expected.getCategory().getName());
                });
    }

}
