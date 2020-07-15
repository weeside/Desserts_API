package com.weecode.desserts.domain.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ResultRepository extends JpaRepository<Result, Long>, QuerydslPredicateExecutor<Result> {
}
