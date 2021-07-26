package com.example.vkbot.kafka;

import com.example.vkbot.client.BotClient;
import com.example.vkbot.model.KafkaMessage;
import com.example.vkbot.service.AppService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private AppService appService;

    public KafkaConsumer(AppService appService) {
        this.appService = appService;
    }

    @KafkaListener(topics = "response", groupId = "group_id", containerFactory = "kafkaListener")
    public void consume(KafkaMessage kafkaMessage) {
        BotClient botClient = appService.getBotByGroupId(kafkaMessage.getGroupId());
        try {
            botClient.sendMessage(kafkaMessage.getClientId(), kafkaMessage.getText());
        } catch (ClientException | ApiException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
