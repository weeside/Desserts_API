package com.weeside.desserts.external.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weeside.desserts.domain.question.Question;
import com.weeside.desserts.external.dummy.QuestionDummyBuilder;
import com.weeside.desserts.external.question.QuestionApiController;
import com.weeside.desserts.external.question.component.QuestionResponseConverter;
import com.weeside.desserts.external.question.dto.QuestionsResponse;
import com.weeside.desserts.external.question.service.QuestionService;
import com.weeside.desserts.external.result.ResultApiController;
import com.weeside.desserts.external.result.dto.QuestionResultCreateRequest;
import com.weeside.desserts.external.result.dto.QuestionResultResponse;
import com.weeside.desserts.external.result.service.ResultService;
import com.weeside.desserts.test.RestDocsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes =  {QuestionApiController.class, ResultApiController.class})
class DessertApplicationApiRestDocsTest extends RestDocsTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private ResultService resultService;

    private QuestionResponseConverter questionResponseConverter;

    @BeforeEach
    void setUp() {
        this.mockMvc = createMockMvc();
        questionResponseConverter = new QuestionResponseConverter();
    }

    @Test
    @DisplayName("질문을 조회한다.")
    void readQuestions() throws Exception {
        // given
        List<Question> questions = QuestionDummyBuilder.create(3);
        QuestionsResponse questionsResponse = questionResponseConverter.convert(questions);

        given(questionService.readQuestions(PageRequest.of(0, 20)))
                .willReturn(CompletableFuture.completedFuture(questionsResponse));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/v1/questions")
                .param("page", "0")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(document("questions",
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호, 기본적으로 20개씩 질문을 제공한다. default: 0")
                                ),
                                responseFields(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description(""),
                                        fieldWithPath("data.questions").type(JsonFieldType.ARRAY).description("질문 목록"),
                                        fieldWithPath("data.questions[].contents").type(JsonFieldType.STRING).description("질문 내용"),
                                        fieldWithPath("data.questions[].yes").type(JsonFieldType.NUMBER).description("해당 질문에 '예'라고 응답했을때 점수"),
                                        fieldWithPath("data.questions[].no").type(JsonFieldType.NUMBER).description("해당 질문에 '아니오'라고 응답했을때 점수")
                                )
                        )
                );
    }

    @Test
    @DisplayName("질문 결과를 전달한다.")
    void createResult() throws Exception {
        // given
        Long memberId = 1L;
        QuestionResultCreateRequest questionResultCreateRequest = QuestionResultCreateRequest.builder()
                .result(5)
                .build();
        QuestionResultResponse questionResultResponse = QuestionResultResponse.success();

        given(resultService.create(eq(memberId), any(QuestionResultCreateRequest.class)))
                .willReturn(CompletableFuture.completedFuture(questionResultResponse));

        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/results")
                .queryParam("memberId", memberId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionResultCreateRequest))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(document("results",
                                requestFields(
                                        fieldWithPath("result").type(JsonFieldType.NUMBER).description("5가지 질문의 총 점수")
                                ),
                                responseFields(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description(""),
                                        fieldWithPath("data.success").type(JsonFieldType.BOOLEAN).description("저장 성공 여부")
                                )
                        )
                );
    }
}