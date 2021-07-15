package com.example.vkbot;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VkBotApplication {

    public static void main(String[] args) throws ClientException, InterruptedException, ApiException {
        SpringApplication.run(VkBotApplication.class, args);
    }
}
