package com.example.vkbot.config;

import com.example.vkbot.service.BotService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BotInit {

    private BotService botService;

    @Autowired
    public void setBotService(BotService botService) {
        this.botService = botService;
    }

    @Bean
    public void startBot() throws ClientException, InterruptedException, ApiException {
        botService.start();
    }
}
