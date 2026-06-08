package com.sport.sportsgroups.repository;

import com.sport.sportsgroups.entity.SportsGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SportsGroupRepository extends JpaRepository<SportsGroup, Long> {
    List<SportsGroup> findByOwnerId(Long ownerId);
}