package com.weecode.desserts.test;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Tag("develop")
@ActiveProfiles("local")
@SpringBootTest
public abstract class DevelopTest {
}
