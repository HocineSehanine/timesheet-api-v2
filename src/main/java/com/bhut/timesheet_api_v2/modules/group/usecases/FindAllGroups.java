package com.bhut.timesheet_api_v2.modules.group.usecases;

import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.exceptions.UnauthorizedException;
import com.bhut.timesheet_api_v2.helpers.CheckGroup;
import com.bhut.timesheet_api_v2.modules.group.entities.GroupEntity;
import com.bhut.timesheet_api_v2.modules.group.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllGroups {

    private final GroupRepository groupRepository;
    private final CheckGroup checkGroup;

    public FindAllGroups(final GroupRepository groupRepository, final CheckGroup checkGroup) {
        this.groupRepository = groupRepository;
        this.checkGroup = checkGroup;
    }

    public List<GroupEntity> execute(final String groupId) {
        final var group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            throw new ResourceNotFoundException("Declared group not found.");
        }
        if (checkGroup.checkIfAdmin(groupId)) {
            throw new UnauthorizedException();
        }

        if (groupRepository.findAll().isEmpty()) {
            return List.of();
        }
        return groupRepository.findAll();
    }
}
