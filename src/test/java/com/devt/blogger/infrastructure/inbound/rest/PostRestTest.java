package com.devt.blogger.infrastructure.inbound.rest;

import com.devt.blogger.domain.entities.Category;
import com.devt.blogger.domain.entities.Post;
import com.devt.blogger.infrastructure.inbound.rest.dto.PostRequest;
import com.devt.blogger.infrastructure.outbound.database.dao.CategoryDao;
import com.devt.blogger.infrastructure.outbound.database.dao.PostDao;
import com.devt.blogger.utils.TestObject;
import com.devt.blogger.utils.rest.RestIntegrationTest;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.devt.blogger.infrastructure.inbound.rest.CategoryRestTest.assertCategory;
import static com.devt.blogger.utils.TestObject.aCategory;
import static com.devt.blogger.utils.TestObject.aPost;
import static com.devt.blogger.utils.TestObject.listOf;
import static com.devt.blogger.utils.rest.RestAssuredHelper.get;
import static com.devt.blogger.utils.rest.RestAssuredHelper.post;
import static com.devt.blogger.utils.rest.ValidatableResponseAssert.assertThat;
import static com.devt.randomizer.RandomizerUtils.random;
import static org.assertj.core.api.Assertions.within;

class PostRestTest extends RestIntegrationTest {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PostDao postDao;

    @Test
    @Sql("/sql/clean.sql")
    void shouldReturnAllPosts() {
        // Arrange
        List<Post> posts = listOf(TestObject::aPost, 5);
        posts.forEach(post -> {
            categoryDao.create(post.getCategory());
            postDao.create(post);
        });
        // Act
        ValidatableResponse response = get("api/v1/posts");
        // Assert
        assertThat(response)
                .hasStatusCode(200)
                .hasArraySize(5)
                .satisfiesInArray(0, Post.class, post -> assertPost(post, posts.getFirst()))
                .satisfiesInArray(1, Post.class, post -> assertPost(post, posts.get(1)))
                .satisfiesInArray(2, Post.class, post -> assertPost(post, posts.get(2)))
                .satisfiesInArray(3, Post.class, post -> assertPost(post, posts.get(3)))
                .satisfiesInArray(4, Post.class, post -> assertPost(post, posts.get(4)));
    }

    @Test
    void shouldReturnPostById() {
        // Arrange
        Post post = aPost();
        categoryDao.create(post.getCategory());
        postDao.create(post);
        // Act
        ValidatableResponse response = get("api/v1/posts/%s".formatted(post.getId()));
        // Assert
        assertThat(response)
                .hasStatusCode(200)
                .containsBodyProperty("id", post.getId().toString())
                .containsBodyProperty("title", post.getTitle())
                .containsBodyProperty("content", post.getContent())
                .containsBodyProperty("category.id", post.getCategory().getId().toString())
                .containsBodyProperty("category.name", post.getCategory().getName());
        Assertions.assertThat(LocalDateTime.parse(response.extract().path("createdDate"))).isEqualTo(post.getCreatedDate());
    }

    @Test
    void shouldReturnNotFoundPostById() {
        // Arrange
        UUID postId = UUID.randomUUID();
        // Act
        ValidatableResponse response = get("api/v1/posts/%s".formatted(postId));
        // Assert
        assertThat(response)
                .hasStatusCode(404)
                .hasNotFoundErrorResponse("Post with id " + postId + " not found");
    }

    @Test
    void shouldReturnNotFoundCategoryById() {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        PostRequest postRequest = new PostRequest(
                random(String.class),
                random(String.class),
                categoryId
        );
        // Act
        ValidatableResponse response = post("api/v1/posts", postRequest);
        // Assert
        assertThat(response)
                .hasStatusCode(404)
                .hasNotFoundErrorResponse("Category with id " + categoryId + " not found");
    }

    @Test
    void shouldReturnCreatedPost() {
        // Arrange
        Category category = aCategory();
        categoryDao.create(category);
        PostRequest postRequest = new PostRequest(
                random(String.class),
                random(String.class),
                category.getId()
        );
        // Act
        ValidatableResponse response = post("api/v1/posts", postRequest);
        // Assert
        assertThat(response)
                .hasStatusCode(201)
                .containsBodyNotNullKeys("id")
                .containsBodyProperty("title", postRequest.title())
                .containsBodyProperty("content", postRequest.content())
                .containsBodyProperty("category.id", category.getId().toString())
                .containsBodyProperty("category.name", category.getName());
        Assertions.assertThat(LocalDateTime.parse(response.extract().path("createdDate"))).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    private void assertPost(Post actual, Post expected) {
        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        Assertions.assertThat(actual.getContent()).isEqualTo(expected.getContent());
        Assertions.assertThat(actual.getCreatedDate()).isEqualTo(expected.getCreatedDate());
        assertCategory(actual.getCategory(), expected.getCategory());
    }

}
