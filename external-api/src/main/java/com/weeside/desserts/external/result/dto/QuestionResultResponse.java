package com.weeside.desserts.external.result.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResultResponse {

    private boolean success;

    private QuestionResultResponse(boolean success) {
        this.success = success;
    }

    public static QuestionResultResponse success() {
        return new QuestionResultResponse(true);
    }

    public static QuestionResultResponse fail() {
        return new QuestionResultResponse(false);
    }
}
