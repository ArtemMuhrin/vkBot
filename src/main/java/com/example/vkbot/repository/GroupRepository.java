package com.example.vkbot.repository;

import com.example.vkbot.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByActiveTrue();
    Group findByGroupId(Integer groupId);
}
