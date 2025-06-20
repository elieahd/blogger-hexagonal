package com.devt.blogger.infrastructure.inbound.rest.dto;

public record ErrorResponse(String errorCode,
                            String errorMessage) {
}
