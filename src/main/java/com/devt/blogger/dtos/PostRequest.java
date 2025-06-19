package com.devt.blogger.dtos;

import java.util.UUID;

public record PostRequest(String title,
                          String content,
                          UUID categoryId) {
}
