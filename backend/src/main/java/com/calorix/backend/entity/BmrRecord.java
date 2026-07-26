package com.calorix.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bmr_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BmrRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false)
    private Double bmr;

    @Column(nullable = false, length = 30)
    private String activityLevel;

    @Column(nullable = false)
    private Double maintenanceCalories;

    @Column(nullable = false)
    private LocalDate recordDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}