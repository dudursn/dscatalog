package io.github.dudursn.dscatalog.services.discord.message;

import io.github.dudursn.dscatalog.constants.TypeMessage;
import io.github.dudursn.dscatalog.interfaces.DiscordMessage;
import io.github.dudursn.dscatalog.services.discord.DiscordWebHookClient;

import java.time.Instant;

public class DiscordMessageError implements DiscordMessage {

    @Override
    public void webHookMessageToDiscord(TypeMessage typeMessage, Object obj, String msgError) {
        if(typeMessage == TypeMessage.ERROR){
            DiscordWebHookClient.execute(
                    "ERROR!: "+ msgError + " ," + obj + ", Time: " + Instant.now().toString());
        }
    }
}
