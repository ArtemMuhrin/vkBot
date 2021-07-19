package com.example.vkbot.service;

import com.vk.api.sdk.objects.messages.Message;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BotService {

    public Map<Integer, String> processMessages(List<Message> messages) {
        Map<Integer, String> result = new HashMap<>();
        messages.forEach(message -> result.put(message.getFromId(), message.getText()));
        return result;
    }
}
