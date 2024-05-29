package com.bhut.timesheet_api_v2.modules.holiday.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@Entity(name = "feriados")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HolidayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "ano", nullable = false)
    private int year;

    @Column(name = "data", nullable = false)
    private LocalDate date;

    @Column(name = "mes")
    private int month;
}
