package com.sport.sportsgroups.repository;

import com.sport.sportsgroups.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
    List<Characteristic> findByGroupId(Long groupId);
}