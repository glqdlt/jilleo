package com.glqdlt.myhome.jilleo;

class NotificationMessageTest {


    void name() {
        String api = "webhookUrl";
        NotificationSender.DiscordChannelWebhook discordWebhookSubmitMessage  =new NotificationSender.DiscordChannelWebhook(api);
        discordWebhookSubmitMessage.submit("hi");
    }
}