package com.weeside.desserts.external.question.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResponse {
    private String contents;
    private Integer yes;
    private Integer no;

    @Builder
    private QuestionResponse(String contents, Integer yes, Integer no) {
        this.contents = contents;
        this.yes = yes;
        this.no = no;
    }
}
