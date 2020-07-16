package com.weecode.desserts.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

    private String message;

    @Builder
    private ErrorMessage(String message) {
        this.message = message;
    }
}
