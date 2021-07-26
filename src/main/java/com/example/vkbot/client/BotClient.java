package com.example.vkbot.client;

import com.example.vkbot.service.BotService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Scope("prototype")
public class BotClient implements Runnable {

    private final BotService botService;
    private VkApiClient vkApiClient;
    private GroupActor groupActor;
    private Random random = new Random();
    private boolean isRunning;
    private Integer ts;

    public BotClient(BotService botService) {
        this.botService = botService;
    }

    @SneakyThrows
    @Override
    public void run() {
        setRunning(true);
        readLongPoolHistory();
    }

    public void stop() {
        setRunning(false);
    }

    public void setProperties(VkApiClient vkApiClient, GroupActor groupActor) {
        this.vkApiClient = vkApiClient;
        this.groupActor = groupActor;
    }

    private void readLongPoolHistory() throws ClientException, ApiException, InterruptedException {
        increaseTs();
        while (isRunning) {
            MessagesGetLongPollHistoryQuery historyQuery = vkApiClient.messages().getLongPollHistory(groupActor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()) {
                botService.processMessages(groupActor.getGroupId(), messages);
            }
            increaseTs();
            Thread.sleep(100);
        }
    }

    public void sendMessage(Integer userId, String text) throws ClientException, ApiException, InterruptedException {
        vkApiClient.messages()
                .send(groupActor)
                .message(text)
                .userId(userId)
                .randomId(random.nextInt(10000))
                .execute();
        increaseTs();
    }

    private void setRunning(boolean running) {
        isRunning = running;
    }

    private void increaseTs() throws ClientException, ApiException {
        ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
    }
}

