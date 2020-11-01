package com.weeside.desserts.domain;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@Tag("repository-test")
@DataJpaTest
@ActiveProfiles("local")
public abstract class RepositoryTest {
}
