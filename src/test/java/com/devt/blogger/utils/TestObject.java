package com.devt.blogger.utils;

import com.devt.blogger.models.Category;
import com.devt.blogger.models.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.devt.randomizer.RandomizerUtils.random;

public class TestObject {

    private TestObject() {
        // utility class shouldn't be instantiated
    }

    public static <T> List<T> listOf(Supplier<T> randomizer, int size) {
        return IntStream.range(0, size)
                .boxed()
                .map(_ -> randomizer.get())
                .toList();
    }

    public static <T> List<T> listOf(Supplier<T> randomizer) {
        return listOf(randomizer, 5);
    }

    public static Category aCategory() {
        return new Category(UUID.randomUUID(), random(String.class));
    }

    public static Post aPost() {
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setTitle(random(String.class));
        post.setContent(random(String.class));
        post.setCategory(aCategory());
        post.setCreatedDate(random(LocalDateTime.class));
        return post;
    }
}