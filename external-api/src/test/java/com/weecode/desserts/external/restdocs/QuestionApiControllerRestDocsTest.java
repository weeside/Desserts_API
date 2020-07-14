package com.weecode.desserts.external.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weecode.desserts.test.RestDocsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class QuestionApiControllerRestDocsTest extends RestDocsTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = createMockMvc();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("질문을 조회한다.")
    void readQuestions() throws Exception {
        // given

        // when
        mockMvc.perform(get("/v1/questions"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
    }
}