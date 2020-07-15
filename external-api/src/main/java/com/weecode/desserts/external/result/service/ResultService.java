package com.weecode.desserts.external.result.service;

import com.weecode.desserts.domain.result.Result;
import com.weecode.desserts.domain.result.ResultRepository;
import com.weecode.desserts.external.result.dto.QuestionResultCreateRequest;
import com.weecode.desserts.external.result.dto.QuestionResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Async
    @Transactional
    public CompletableFuture<QuestionResultResponse> create(QuestionResultCreateRequest questionResultCreateRequest) {
        return CompletableFuture.supplyAsync(() -> {
                    Result result = Result.builder()
                            .point(questionResultCreateRequest.getResult())
                            .build();

                    resultRepository.save(result);
                    return QuestionResultResponse.success();
                })
                .exceptionally(error -> {
                    log.error("result save failed. request: {}", questionResultCreateRequest, error);
                    return QuestionResultResponse.fail();
                });
    }
}
