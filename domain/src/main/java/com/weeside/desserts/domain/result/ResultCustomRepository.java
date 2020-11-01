package com.weeside.desserts.domain.result;

import java.time.LocalDateTime;
import java.util.List;

public interface ResultCustomRepository {
    List<Result> findAllByCreatedAt(LocalDateTime createdAt);
}
