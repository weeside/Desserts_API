package com.weeside.desserts.external.result.service;

import com.weeside.desserts.domain.result.Result;
import com.weeside.desserts.domain.result.ResultRepository;
import com.weeside.desserts.external.memberstat.component.MemberStatisticsSaver;
import com.weeside.desserts.external.result.dto.QuestionResultCreateRequest;
import com.weeside.desserts.external.result.dto.QuestionResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import static com.weeside.desserts.external.config.AsyncConfig.EXTERNAL_API_ASYNC_THREAD_POOL_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final MemberStatisticsSaver memberStatisticsSaver;

    @Async(EXTERNAL_API_ASYNC_THREAD_POOL_NAME)
    @Transactional
    public CompletableFuture<QuestionResultResponse> create(Long memberId,
                                                            QuestionResultCreateRequest questionResultCreateRequest) {
        Result result = Result.builder()
                .point(questionResultCreateRequest.getResult())
                .build();

        CompletableFuture<QuestionResultResponse> completableFuture = new CompletableFuture<>();

        try {
            resultRepository.save(result);
            memberStatisticsSaver.saveByResultCreation(memberId, result);
            completableFuture.complete(QuestionResultResponse.success());
        } catch (Exception ex) {
            log.error("result save failed. request: {}", questionResultCreateRequest, ex);
            completableFuture.complete(QuestionResultResponse.fail());
        }

        return completableFuture;
    }
}
