package com.weeside.desserts.external.insight.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsightResponse {

    private String message;

    @Builder
    public InsightResponse(String message) {
        this.message = message;
    }
}
