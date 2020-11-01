package com.weeside.desserts.external.insight;

import com.weeside.desserts.external.insight.dto.InsightResponse;
import com.weeside.desserts.external.insight.service.InsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/insights")
@RequiredArgsConstructor
public class InsightApiController {

    private final InsightService insightService;

    @GetMapping
    public CompletableFuture<InsightResponse> readInsight(Long memberId) {
        return insightService.getInsight(memberId);
    }
}
