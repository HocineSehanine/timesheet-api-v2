package com.bhut.timesheet_api_v2.helpers;

import com.bhut.timesheet_api_v2.modules.group.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckGroup {

    private final GroupRepository groupRepository;

    public CheckGroup(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public boolean checkIfAdmin(final String groupId) {
        final var group = groupRepository.findById(groupId).orElse(null);
        assert group != null;
        return !group.getName().equals("Administrador");
    }
}
