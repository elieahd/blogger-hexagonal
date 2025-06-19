package com.devt.blogger.services;

import com.devt.blogger.exceptions.CategoryNotFoundByIdException;
import com.devt.blogger.exceptions.PostNotFoundByIdException;
import com.devt.blogger.models.Category;
import com.devt.blogger.models.Post;
import com.devt.blogger.repositories.CategoryRepository;
import com.devt.blogger.repositories.PostRepository;
import com.devt.blogger.utils.TestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.devt.blogger.utils.TestObject.aCategory;
import static com.devt.blogger.utils.TestObject.aPost;
import static com.devt.blogger.utils.TestObject.listOf;
import static com.devt.randomizer.RandomizerUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceTest {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private PostService postService;

    @BeforeEach
    void setup() {
        categoryRepository = mock();
        postRepository = mock();
        postService = new PostService(postRepository, categoryRepository);
    }

    @Test
    void shouldReturnPosts() {
        // Arrange
        List<Post> expectedPosts = listOf(TestObject::aPost);
        when(postRepository.findAll()).thenReturn(expectedPosts);
        // Act
        List<Post> posts = postService.getPosts();
        // Assert
        assertThat(posts).isEqualTo(expectedPosts);
    }

    @Test
    void shouldThrowPostNotFoundByIdException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(null);
        // Act
        Throwable thrown = catchThrowable(() -> postService.getById(id));
        // Assert
        assertThat(thrown).isInstanceOf(PostNotFoundByIdException.class)
                .hasMessage("Post with id " + id + " not found");
    }

    @Test
    void shouldReturnPostById() throws PostNotFoundByIdException {
        // Arrange
        UUID id = UUID.randomUUID();
        Post expectedPost = aPost();
        when(postRepository.findById(id)).thenReturn(expectedPost);
        // Act
        Post post = postService.getById(id);
        // Assert
        assertThat(post).isEqualTo(expectedPost);
    }

    @Test
    void shouldThrowCategoryNotFoundByIdExceptionWhenCreating() {
        // Arrange
        String title = random(String.class);
        String content = random(String.class);
        UUID categoryId = UUID.randomUUID();
        when(categoryRepository.findById(categoryId)).thenReturn(null);
        // Act
        Throwable thrown = catchThrowable(() -> postService.create(title, content, categoryId));
        // Assert
        assertThat(thrown).isInstanceOf(CategoryNotFoundByIdException.class)
                .hasMessage("Category with id " + categoryId + " not found");
        verify(postRepository, never()).create(any());
    }

    @Test
    void shouldReturnCreatedPost() throws CategoryNotFoundByIdException {
        // Arrange
        String title = random(String.class);
        String content = random(String.class);
        UUID categoryId = UUID.randomUUID();
        Category category = aCategory();
        when(categoryRepository.findById(categoryId)).thenReturn(category);
        // Act
        Post post = postService.create(title, content, categoryId);
        // Assert
        assertThat(post.getId()).isNotNull();
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getCategory()).isEqualTo(category);
        assertThat(post.getCreatedDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        verify(postRepository).create(any());
    }

}
