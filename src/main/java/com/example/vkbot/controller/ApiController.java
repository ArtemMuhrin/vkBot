package com.example.vkbot.controller;

import com.example.vkbot.model.Group;
import com.example.vkbot.service.AppService;
import com.example.vkbot.service.GroupService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    private final GroupService groupService;
    private final AppService appService;

    public ApiController(GroupService groupService, AppService appService) {
        this.groupService = groupService;
        this.appService = appService;
    }

    @PostMapping("/group")
    public ResponseEntity<?> saveGroup(@RequestParam Integer groupId, @RequestParam String token) {
        Group group = new Group(groupId, token, true);
        try {
            groupService.saveGroup(group);
            appService.startBot(group);
            return ResponseEntity.ok("Group has been added. Bot started working.");
        } catch (ClientException | ApiException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Service error. Please try later.");
        }
    }

    @DeleteMapping("/group")
    public ResponseEntity<?> deleteGroup(@RequestParam Integer groupId, @RequestParam String token) {
        Group group = groupService.findByGroupId(groupId);
        if (group == null) {
            return ResponseEntity.badRequest().body("Group not found.");
        } else if (!token.equals(group.getToken())) {
            return ResponseEntity.badRequest().body("Invalid token.");
        } else {
            groupService.deleteGroup(group);
            appService.stopBot(group);
            return ResponseEntity.ok("Group has been deleted. Bot is disabled.");
        }
    }
}
