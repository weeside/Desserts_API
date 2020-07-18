package com.weecode.desserts.external.question;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weecode.desserts.common.dto.DessertsResponse;
import com.weecode.desserts.domain.question.Question;
import com.weecode.desserts.domain.question.QuestionRepository;
import com.weecode.desserts.external.dummy.QuestionDummyBuilder;
import com.weecode.desserts.external.question.component.QuestionResponseConverter;
import com.weecode.desserts.external.question.dto.QuestionsResponse;
import com.weecode.desserts.test.ContextTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class QuestionApiControllerTest extends ContextTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private QuestionResponseConverter questionResponseConverter;


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

    @Test
    @DisplayName("서버 에러 발생시 500응답과 함께 메세지를 띄워준다.")
    void readQuestions_ifOccurredServerException_thenResponseInternalServerException() throws Exception {
        // given
        List<Question> questions = QuestionDummyBuilder.create(50);
        questionRepository.saveAll(questions);
        given(questionResponseConverter.convert(anyList())).willThrow(RuntimeException.class);

        // when
        MvcResult result = mockMvc.perform(
                get("/v1/questions")
                        .queryParam("page", "0")
        )
                .andDo(print())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.data.message").value(Matchers.is("일시적으로 오류가 발생했습니다.")));
    }
}