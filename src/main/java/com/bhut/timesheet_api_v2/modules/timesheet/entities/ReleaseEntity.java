package com.bhut.timesheet_api_v2.modules.timesheet.entities;

import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
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

@Entity(name = "lancamentos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "id_usuario", nullable = false, columnDefinition = "CHAR(36)")
    private String userId;

    @Column(name = "data", nullable = false)
    private LocalDate date;

    @Column(name = "feriado", nullable = false)
    private Boolean holiday;

    @Column(name = "hora_inicio")
    private String startTime;

    @Column(name = "hora_fim")
    private String endTime;

    @Column(name = "total")
    private String total;

    @Column(name = "descricao")
    private String description;

    @Column(name = "ano")
    private int year;

    @Column(name = "mes")
    private int month;
}
