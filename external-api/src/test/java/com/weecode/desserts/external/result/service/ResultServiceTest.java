package com.weecode.desserts.external.result.service;

import com.weecode.desserts.domain.result.Result;
import com.weecode.desserts.domain.result.ResultRepository;
import com.weecode.desserts.external.result.dto.QuestionResultCreateRequest;
import com.weecode.desserts.external.result.dto.QuestionResultResponse;
import com.weecode.desserts.external.result.service.ResultService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @InjectMocks
    private ResultService dut;

    @Mock
    private ResultRepository resultRepository;

    @Test
    @DisplayName("질문 응답 결과를 저장한다.")
    void create() {
        // given
        Result result = Result.builder()
                .point(5.0)
                .build();

        given(resultRepository.save(any(Result.class))).willReturn(result);

        // when
        QuestionResultCreateRequest request = QuestionResultCreateRequest.builder()
                .result(5.0)
                .build();

        QuestionResultResponse actual = dut.create(request).join();

        // then
        assertTrue(actual.isSuccess());
        then(resultRepository).should().save(any(Result.class));
    }

    @Test
    @DisplayName("질문 응답 결과를 저장시 에러가 발생하면 success룰 false로 반환한다.")
    void create_ifOccurredException_thenSuccessIsFalse() {
        // given
        given(resultRepository.save(any(Result.class))).willThrow(RuntimeException.class);

        // when
        QuestionResultCreateRequest request = QuestionResultCreateRequest.builder()
                .result(5.0)
                .build();

        QuestionResultResponse actual = dut.create(request).join();

        // then
        assertFalse(actual.isSuccess());
        then(resultRepository).should().save(any(Result.class));
    }
}