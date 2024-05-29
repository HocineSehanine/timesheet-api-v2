package com.bhut.timesheet_api_v2.modules.users.entities;

import com.bhut.timesheet_api_v2.modules.group.entities.GroupEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "usuarios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "grupo_id", insertable = false, updatable = false)
    private GroupEntity group;

    @Column(name = "grupo_id", nullable = false, columnDefinition = "CHAR(36)")
    private String groupId;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String password;

    @Column(name = "time", nullable = false)
    private String team;

    @Column(name = "valor_hora", nullable = false)
    private BigDecimal hourValue;

    @Column(name = "banco_horas", nullable = false)
    private boolean hasBankHours;

    @Column(name = "contrato_mensal", nullable = false)
    private int contractTotal;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate startDate;
}
