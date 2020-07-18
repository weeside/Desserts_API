package com.weeside.desserts.external.question.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionsResponse {
    private List<QuestionResponse> questions;

    @Builder
    private QuestionsResponse(List<QuestionResponse> questions) {
        this.questions = questions;
    }
}
