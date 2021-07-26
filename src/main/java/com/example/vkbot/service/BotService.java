package com.example.vkbot.service;

import com.example.vkbot.kafka.KafkaProducer;
import com.example.vkbot.model.KafkaMessage;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotService {

    private KafkaProducer kafkaProducer;

    public BotService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void processMessages(Integer groupId, List<Message> messages) {
        messages.forEach(message -> {
            if (!message.isOut()) {
                kafkaProducer.send(new KafkaMessage(groupId, message.getFromId(), message.getText()));
            }
        });
    }
}
