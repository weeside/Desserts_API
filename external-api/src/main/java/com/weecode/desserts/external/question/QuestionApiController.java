package com.weecode.desserts.external.question;

import com.weecode.desserts.common.dto.DessertsResponse;
import com.weecode.desserts.external.question.dto.QuestionsResponse;
import com.weecode.desserts.external.question.service.QuestionService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/questions")
public class QuestionApiController {

    private final QuestionService questionService;

    public QuestionApiController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public CompletableFuture<DessertsResponse<QuestionsResponse>> questions(Pageable pageable) {
        return questionService.readQuestions(pageable)
                .thenCompose(questionsResponse -> CompletableFuture.supplyAsync(() -> DessertsResponse.success(questionsResponse)));
    }
}
