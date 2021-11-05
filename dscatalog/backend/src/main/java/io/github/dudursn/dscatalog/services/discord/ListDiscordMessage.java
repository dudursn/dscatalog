package io.github.dudursn.dscatalog.services.discord;

import io.github.dudursn.dscatalog.interfaces.DiscordMessage;
import io.github.dudursn.dscatalog.services.discord.message.DiscordMessageDelete;
import io.github.dudursn.dscatalog.services.discord.message.DiscordMessageError;
import io.github.dudursn.dscatalog.services.discord.message.DiscordMessageInsert;
import io.github.dudursn.dscatalog.services.discord.message.DiscordMessageUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public final class ListDiscordMessage {

    public static List<DiscordMessage> loadList(){

        List<DiscordMessage> discordMessages = new ArrayList<>();

        DiscordMessageDelete discordMessageDelete = new DiscordMessageDelete();
        discordMessages.add(discordMessageDelete);

        DiscordMessageError discordMessageError = new DiscordMessageError();
        discordMessages.add(discordMessageError);

        DiscordMessageUpdate discordMessageUpdate = new DiscordMessageUpdate();
        discordMessages.add(discordMessageUpdate);

        DiscordMessageInsert discordMessageInsert = new DiscordMessageInsert();
        discordMessages.add(discordMessageInsert);

        return discordMessages;
    }

}
