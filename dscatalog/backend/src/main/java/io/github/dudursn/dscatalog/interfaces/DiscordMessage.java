package io.github.dudursn.dscatalog.interfaces;

import io.github.dudursn.dscatalog.constants.TypeMessage;

public interface DiscordMessage {

    public void webHookMessageToDiscord(TypeMessage typeMessage, Object obj, String msgError);
}
