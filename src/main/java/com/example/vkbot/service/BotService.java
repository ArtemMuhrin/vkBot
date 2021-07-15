package com.example.vkbot.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@PropertySource("classpath:application.properties")
public class BotService {

    @Value("${vk.groupId}")
    private Integer groupId;
    @Value("${vk.token}")
    private String token;
    private GroupActor groupActor;
    private final TransportClient transportClient = HttpTransportClient.getInstance();
    private final VkApiClient vkApiClient = new VkApiClient(transportClient);

    public void start() throws ClientException, InterruptedException, ApiException {
        groupActor = new GroupActor(groupId, token);
        processMessages();
    }

    private void processMessages() throws ClientException, ApiException, InterruptedException {
        Random random = new Random();
        Integer ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
        while (true) {
            MessagesGetLongPollHistoryQuery historyQuery = vkApiClient.messages().getLongPollHistory(groupActor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()) {
                messages.forEach(message -> {
                    try {
                        vkApiClient.messages()
                                .send(groupActor)
                                .message(message.getText())
                                .userId(message.getFromId())
                                .randomId(random.nextInt(10000))
                                .execute();
                    } catch (ApiException | ClientException e) {
                        e.printStackTrace();
                    }
                });
            }
            ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
            Thread.sleep(500);
        }
    }
}
