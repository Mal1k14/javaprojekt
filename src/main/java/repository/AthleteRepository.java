package com.sport.sportsgroups.repository;

import com.sport.sportsgroups.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    List<Athlete> findByGroupId(Long groupId);
}