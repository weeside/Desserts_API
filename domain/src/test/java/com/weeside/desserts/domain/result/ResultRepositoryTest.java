package com.weeside.desserts.domain.result;

import com.weeside.desserts.domain.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResultRepositoryTest extends RepositoryTest {

    @Autowired
    private ResultRepository dut;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("createdAt보다 이후에 생성된 Result들을 조회한다.")
    void findAllByCreatedAt() {
        // given
        LocalDateTime createdAt = LocalDateTime.of(2020, 10, 1, 0, 0, 0);

        Result result = Result.builder()
                .point(5)
                .build();
        ReflectionTestUtils.setField(result, "createdAt", createdAt.plusDays(1));
        testEntityManager.persist(result);

        Result outdatedResult = Result.builder()
                .point(5)
                .build();
        ReflectionTestUtils.setField(outdatedResult, "createdAt", createdAt.minusDays(1));
        testEntityManager.persist(outdatedResult);

        // when
        List<Result> actual = dut.findAllByCreatedAt(createdAt);

        // then
        assertThat(actual).hasSize(1);
    }
}