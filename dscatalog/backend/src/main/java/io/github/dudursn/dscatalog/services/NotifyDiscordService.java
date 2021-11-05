package io.github.dudursn.dscatalog.services;

import io.github.dudursn.dscatalog.constants.TypeMessage;
import io.github.dudursn.dscatalog.interfaces.DiscordMessage;
import io.github.dudursn.dscatalog.services.discord.ListDiscordMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyDiscordService {

    List<DiscordMessage> discordMessages;

    public NotifyDiscordService(List<DiscordMessage> discordMessages) {
        this.discordMessages = ListDiscordMessage.loadList();
    }

    public void notify(TypeMessage typeMessage, Object obj, String msgError){

        discordMessages.forEach(discordMessage -> {

            discordMessage.webHookMessageToDiscord(typeMessage, obj, msgError);
        });

    }
}
