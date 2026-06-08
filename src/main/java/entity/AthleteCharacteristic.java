package com.sport.sportsgroups.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "athlete_characteristics")
@Data
@JsonIdentityInfo(  // ← ДОБАВИТЬ ЭТО
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class AthleteCharacteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "char_value")
    private int value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "characteristic_id", nullable = false)
    private Characteristic characteristic;
}