package com.devt.blogger.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Post {

    private UUID id;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime createdDate;

    public Post() {
    }

    public Post(String title,
                String content,
                Category category) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
