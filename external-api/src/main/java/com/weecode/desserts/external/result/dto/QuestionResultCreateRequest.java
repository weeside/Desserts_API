package com.weecode.desserts.external.result.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResultCreateRequest {

    private double result;

    @Builder
    private  QuestionResultCreateRequest(double result) {
        this.result = result;
    }
}
