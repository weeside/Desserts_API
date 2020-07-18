package com.weeside.desserts;

import com.weeside.desserts.common.dto.DessertsResponse;
import com.weeside.desserts.common.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.weeside.desserts.CommonControllerAdvice.COMMON_ADVICE_PRIORITY;

@Slf4j
@RestControllerAdvice
@Order(COMMON_ADVICE_PRIORITY)
public class CommonControllerAdvice {
    public static final int COMMON_ADVICE_PRIORITY = 99;

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DessertsResponse<ErrorMessage> handleInternalServerError(Exception exception) {
        log.error("error occured. {}", exception.getMessage(), exception);

        return DessertsResponse.fail(
                ErrorMessage.builder()
                        .message("일시적으로 오류가 발생했습니다.")
                        .build()
        );
    }
}
