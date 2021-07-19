package com.example.vkbot.service;

import com.example.vkbot.model.Group;
import com.example.vkbot.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void saveGroup(Group group) {
        Group currentGroup = findByGroupId(group.getGroupId());
        if (currentGroup == null) {
            groupRepository.save(group);
        } else if (!currentGroup.getActive()) {
            currentGroup.setActive(true);
            groupRepository.save(currentGroup);
        }
    }

    public Group findByGroupId(Integer groupId) {
        return groupRepository.findByGroupId(groupId);
    }

    public void deleteGroup(Group group) {
        group.setActive(false);
        groupRepository.save(group);
    }

    public List<Group> getAllActiveGroups() {
        return groupRepository.findByActiveTrue();
    }
}
