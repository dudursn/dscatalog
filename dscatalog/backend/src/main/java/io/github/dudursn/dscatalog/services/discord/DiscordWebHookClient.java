package io.github.dudursn.dscatalog.services.discord;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class DiscordWebHookClient {

    public void execute(String msg){

        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/" +
                "898625534471340112/bCNpFtUE4nF7NpsGXKSqAP10IsNoD04ToGv8L-DlQA7okb5XP8Qg0-sQr2pepwKWQDeS");

        webhook.setContent(msg);
        webhook.setAvatarUrl("https://dudursn.github.io/images/eu.jpg");
        webhook.setUsername("Captain Edu");
        webhook.setTts(true);
        /*
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Title")
                .setDescription("This is a description")
                .setColor(Color.RED)
                .addField("1st Field", "Inline", true)
        .addField("2nd Field", "Inline", true)
        .addField("3rd Field", "No-Inline", false)
        .setThumbnail("https://kryptongta.com/images/kryptonlogo.png")
        .setFooter("Footer text", "https://kryptongta.com/images/kryptonlogodark.png")
        .setImage("https://kryptongta.com/images/kryptontitle2.png")
        .setAuthor("Author Name", "https://kryptongta.com", "https://kryptongta.com/images/kryptonlogowide.png")
        .setUrl("https://kryptongta.com"));
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("Just another added embed object!"));
         */

        webhook.run(); //Handle exception

    }
}
