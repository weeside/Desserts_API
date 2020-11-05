package com.weeside.desserts.domain.result;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

import static com.weeside.desserts.domain.result.QResult.*;

public class ResultCustomRepositoryImpl extends QuerydslRepositorySupport implements ResultCustomRepository {

    public ResultCustomRepositoryImpl() {
        super(Result.class);
    }

    @Override
    public List<Result> findAllByCreatedAt(LocalDateTime createdAt) {
        return from(result)
                .where(
                    result.createdAt.goe(createdAt)
                )
                .fetch();
    }
}
