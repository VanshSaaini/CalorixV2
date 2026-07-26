package com.calorix.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="body_measurements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double neck;

    private Double chest;

    private Double waist;

    private Double hips;

    private Double leftArm;

    private Double rightArm;

    private Double leftThigh;

    private Double rightThigh;

    private Double leftCalf;

    private Double rightCalf;

    @Column(nullable = false)
    private LocalDate recordDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

}