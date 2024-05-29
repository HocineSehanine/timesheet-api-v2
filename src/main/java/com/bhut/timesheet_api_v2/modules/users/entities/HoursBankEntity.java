package com.bhut.timesheet_api_v2.modules.users.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "banco_horas")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoursBankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "id_usuario", nullable = false, columnDefinition = "CHAR(36)")
    private String userId;

    @Column(name = "data", nullable = false)
    private LocalDate date;

    @Column(name = "hora", nullable = false)
    private String hour;

    @Column(name = "descricao")
    private String description;
}
