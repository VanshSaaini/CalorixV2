package com.calorix.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "macro_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MacroRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double calories;

    @Column(nullable = false)
    private Double protein;

    @Column(nullable = false)
    private Double carbohydrates;

    @Column(nullable = false)
    private Double fats;

    @Column(nullable = false, length = 30)
    private String goal;

    @Column(nullable = false)
    private LocalDate recordDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}