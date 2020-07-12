package com.weeside.desserts.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@Tag("restdocs")
@ActiveProfiles(profiles = {"local"})
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest
public abstract class RestDocsTest {
    private WebApplicationContext webApplicationContext;
    private RestDocumentationContextProvider restDocumentationContextProvider;

    @BeforeEach
    public void setUp(WebApplicationContext applicationContext,
                      RestDocumentationContextProvider restDocumentationContextProvider) {
        this.webApplicationContext = applicationContext;
        this.restDocumentationContextProvider = restDocumentationContextProvider;
    }

    protected MockMvc createMockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }
}
