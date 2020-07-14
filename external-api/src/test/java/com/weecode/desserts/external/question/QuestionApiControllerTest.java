package com.weecode.desserts.external.question;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weecode.desserts.common.dto.DessertsResponse;
import com.weecode.desserts.domain.question.Question;
import com.weecode.desserts.domain.question.QuestionRepository;
import com.weecode.desserts.external.dummy.QuestionDummyBuilder;
import com.weecode.desserts.external.question.dto.QuestionsResponse;
import com.weecode.desserts.test.ContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
class QuestionApiControllerTest extends ContextTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("질문을 조회한다.")
    void readQuestions() throws Exception {
        // given
        List<Question> questions = QuestionDummyBuilder.create(50);
        questionRepository.saveAll(questions);

        // when
        MvcResult result = mockMvc.perform(
                get("/v1/questions")
                        .queryParam("page", "0")
        )
                .andDo(print())
                .andReturn();

        DessertsResponse<QuestionsResponse> actual = objectMapper.readValue(mockMvc.perform(asyncDispatch(result))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<DessertsResponse<QuestionsResponse>>() {
                }
        );

        // then
        QuestionsResponse data = actual.getData();
        assertNotNull(data);
        assertThat(data.getQuestions()).hasSize(20);
    }
}