package com.weeside.desserts.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DessertsResponse<T> {

    private T data;

    private DessertsResponse(T data) {
        this.data = data;
    }

    public static <T> DessertsResponse<T> success(T data) {
        return new DessertsResponse<>(data);
    }

    public static DessertsResponse<ErrorMessage> fail(ErrorMessage errorMessage) {
        return new DessertsResponse<>(errorMessage);
    }
}
