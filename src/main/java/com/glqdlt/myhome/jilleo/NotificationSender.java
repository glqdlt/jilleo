package com.glqdlt.myhome.jilleo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author glqdlt
 */
public interface NotificationSender {

    void submit(String message);

    @Component
    class DiscordChannelWebhook implements NotificationSender {

        private String webhookApiUrl;

        public DiscordChannelWebhook(@Value("${dicord.webhook}") String webhookApiUrl) {
            this.webhookApiUrl = webhookApiUrl;
        }

        private final RestTemplate jdbcTemplate = new RestTemplate();

        @Override
        public void submit(String message) {

            MultiValueMap<String,String> payload = new LinkedMultiValueMap<>();
            payload.add("content",message);

            RequestEntity<MultiValueMap<String, String>> req = RequestEntity.post(webhookApiUrl)
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .header("User-Agent","DiscordBot")
                    .body(payload);

            ResponseEntity<String> asd = jdbcTemplate.exchange(req,String.class);
            System.out.println(asd.getStatusCode());




        }
    }


}
