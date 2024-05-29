package com.bhut.timesheet_api_v2.modules.group.entities;

import com.bhut.timesheet_api_v2.modules.auth.entities.ResourceEntity;
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

import java.util.UUID;

@Entity(name = "recursos_grupo")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_recurso", insertable = false, updatable = false)
    private ResourceEntity resource;

    @Column(name = "id_recurso", columnDefinition = "CHAR(36)")
    private UUID resourceId;

    @ManyToOne
    @JoinColumn(name = "id_grupo", insertable = false, updatable = false)
    private GroupEntity group;

    @Column(name = "id_grupo", columnDefinition = "CHAR(36)")
    private UUID groupId;
}