package com.weeside.desserts.external.result.service;

import com.weeside.desserts.domain.result.Result;
import com.weeside.desserts.domain.result.ResultRepository;
import com.weeside.desserts.external.memberstat.component.MemberStatisticsSaver;
import com.weeside.desserts.external.result.dto.QuestionResultCreateRequest;
import com.weeside.desserts.external.result.dto.QuestionResultResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @InjectMocks
    private ResultService dut;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private MemberStatisticsSaver memberStatisticsSaver;

    @Test
    @DisplayName("질문 응답 결과를 저장한다.")
    void create() {
        // given
        Long memberId = 1L;
        Result result = Result.builder()
                .point(5)
                .build();

        given(resultRepository.save(any(Result.class))).willReturn(result);
        willDoNothing().given(memberStatisticsSaver).saveByResultCreation(anyLong(), any(Result.class));

        // when
        QuestionResultCreateRequest request = QuestionResultCreateRequest.builder()
                .result(5)
                .build();

        QuestionResultResponse actual = dut.create(memberId, request).join();

        // then
        assertTrue(actual.isSuccess());
        then(resultRepository).should().save(any(Result.class));
        then(memberStatisticsSaver).should().saveByResultCreation(anyLong(), any(Result.class));
    }

    @Test
    @DisplayName("질문 응답 결과를 저장시 에러가 발생하면 success룰 false로 반환한다.")
    void create_ifOccurredException_thenSuccessIsFalse() {
        // given
        Long memberId = 1L;
        given(resultRepository.save(any(Result.class))).willThrow(RuntimeException.class);

        // when
        QuestionResultCreateRequest request = QuestionResultCreateRequest.builder()
                .result(5)
                .build();

        QuestionResultResponse actual = dut.create(memberId, request).join();

        // then
        assertFalse(actual.isSuccess());
        then(resultRepository).should().save(any(Result.class));
    }
}