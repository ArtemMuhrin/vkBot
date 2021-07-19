package com.example.vkbot.config;

import com.example.vkbot.model.Group;
import com.example.vkbot.service.AppService;
import com.example.vkbot.service.GroupService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppInit {

    private final GroupService groupService;
    private final AppService appService;

    public AppInit(GroupService groupService, AppService appService) {
        this.groupService = groupService;
        this.appService = appService;
    }

    @Bean
    public void startApp() {
        List<Group> activeGroups = groupService.getAllActiveGroups();
        for (Group group : activeGroups) {
            try {
                appService.startBot(group);
            } catch (ClientException | ApiException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
