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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "pagamentos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "id_usuario", nullable = false, columnDefinition = "CHAR(36)")
    private String userId;

    @Column(name = "mes")
    private int month;

    @Column(name = "ano")
    private int year;

    @Column(name = "total_horas")
    private double totalHours;

    @Column(name = "data_pagamento")
    private LocalDate paymentDate;

    @Column(name = "valor_hora")
    private BigDecimal hourValue;

    @Column(name = "valor_total")
    private double totalValue;

    @Column(name = "banco_horas_atual")
    private double currentHourBank;
}
