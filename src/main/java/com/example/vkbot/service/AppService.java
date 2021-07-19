package com.example.vkbot.service;

import com.example.vkbot.client.BotClient;
import com.example.vkbot.model.Group;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppService {

    private ApplicationContext context;
    private final Map<Integer, BotClient> botPool = new HashMap<>();

    public AppService(ApplicationContext context) {
        this.context = context;
    }

    public void startBot(Group group) throws ClientException, InterruptedException, ApiException {
        BotClient botClient = context.getBean("botClient", BotClient.class);
        botClient.setProperties(new VkApiClient(HttpTransportClient.getInstance()), new GroupActor(group.getGroupId(), group.getToken()));
        new Thread(botClient).start();
        botPool.put(group.getGroupId(), botClient);
    }

    public void stopBot(Group group) {
        if (botPool.containsKey(group.getGroupId())) {
            BotClient botClient = botPool.get(group.getGroupId());
            botClient.stop();
            botPool.remove(group.getGroupId());
        }
    }
}
