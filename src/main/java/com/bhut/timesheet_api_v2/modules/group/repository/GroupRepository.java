package com.bhut.timesheet_api_v2.modules.group.repository;

import com.bhut.timesheet_api_v2.modules.group.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, String> {
}
