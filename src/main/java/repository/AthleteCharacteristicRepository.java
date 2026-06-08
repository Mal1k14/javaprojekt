package com.sport.sportsgroups.repository;

import com.sport.sportsgroups.entity.AthleteCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteCharacteristicRepository extends JpaRepository<AthleteCharacteristic, Long> {
}