package com.weeside.desserts.external.result;

import com.weeside.desserts.common.dto.DessertsResponse;
import com.weeside.desserts.external.result.dto.QuestionResultCreateRequest;
import com.weeside.desserts.external.result.dto.QuestionResultResponse;
import com.weeside.desserts.external.result.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/results")
public class ResultApiController {

    private ResultService resultService;

    public ResultApiController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<DessertsResponse<QuestionResultResponse>> createResult(@RequestBody QuestionResultCreateRequest questionResultCreateRequest) {
        return resultService.create(questionResultCreateRequest)
                .thenCompose(questionResultResponse -> CompletableFuture.supplyAsync(() -> DessertsResponse.success(questionResultResponse)));
    }
}
