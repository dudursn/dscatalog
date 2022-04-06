package io.github.dudursn.dscatalog.services.discord.message;

import io.github.dudursn.dscatalog.constants.TypeMessage;
import io.github.dudursn.dscatalog.interfaces.DiscordMessage;
import io.github.dudursn.dscatalog.services.discord.DiscordWebHookClient;

import java.time.Instant;


public class DiscordMessageUpdate implements DiscordMessage {


    @Override
    public void webHookMessageToDiscord(TypeMessage typeMessage, Object obj, String msgError) {
        if(typeMessage == TypeMessage.UPDATE){
            DiscordWebHookClient.execute(
                    "Update: "+ obj + ", Time: " + Instant.now().toString());
        }
    }
}
