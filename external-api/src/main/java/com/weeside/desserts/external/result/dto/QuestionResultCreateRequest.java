package com.weeside.desserts.external.result.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResultCreateRequest {

    private int result;

    @Builder
    private QuestionResultCreateRequest(int result) {
        this.result = result;
    }
}
