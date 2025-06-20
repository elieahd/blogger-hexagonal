package com.devt.blogger.infrastructure.inbound.rest.dto;

import java.util.UUID;

public record PostRequest(String title,
                          String content,
                          UUID categoryId) {
}
