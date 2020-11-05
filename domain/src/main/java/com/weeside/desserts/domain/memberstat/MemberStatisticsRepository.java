package com.weeside.desserts.domain.memberstat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberStatisticsRepository extends JpaRepository<MemberStatistics, Long> {
    Optional<MemberStatistics> findByMemberId(Long memberId);
}
