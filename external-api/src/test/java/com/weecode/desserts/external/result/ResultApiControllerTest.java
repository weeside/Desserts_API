package com.weecode.desserts.external.result;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weecode.desserts.common.dto.DessertsResponse;
import com.weecode.desserts.external.result.dto.QuestionResultCreateRequest;
import com.weecode.desserts.external.result.dto.QuestionResultResponse;
import com.weecode.desserts.test.ContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
class ResultApiControllerTest extends ContextTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("질문 응답 결과를 저장한다.")
    void createResult() throws Exception {
        // given
        QuestionResultCreateRequest questionResultCreateRequest = QuestionResultCreateRequest.builder()
                .result(5.0)
                .build();

        // when
        MvcResult result = mockMvc.perform(
                post("/v1/results")
                .content(objectMapper.writeValueAsString(questionResultCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andReturn();

        String responseBody = mockMvc.perform(asyncDispatch(result))
                .andReturn()
                .getResponse()
                .getContentAsString();

        DessertsResponse<QuestionResultResponse> actual = objectMapper.readValue(responseBody, new TypeReference<DessertsResponse<QuestionResultResponse>>() {});

        // then
        assertTrue(actual.getData().isSuccess());
    }
}