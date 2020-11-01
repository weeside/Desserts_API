package com.weeside.desserts.domain.insight;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsightRepository extends JpaRepository<Insight, Long> {
    List<Insight> findAllByCategory(InsightCategory category);
}
