package io.github.dudursn.dscatalog.services.discord;


public final class DiscordWebHookClient {

    private final static String URL_WEBHOOK = "https://discord.com/api/webhooks/912134816785399828/" +
            "3dIU3zZgcQ9QgPlOiBojY3ixdB9CYQTpzE6nKCZ6Pt3YOI90FHvAkVj8-aiLNGgUIigl";

    private final static String AVATAR_URL = "https://dudursn.github.io/images/eu.jpg";

    private final static String USERNAME = "Captain Edu";

    public static void execute(String msg){

        DiscordWebhook webhook = new DiscordWebhook(URL_WEBHOOK);

        webhook.setContent(msg);
        webhook.setAvatarUrl(AVATAR_URL);
        webhook.setUsername(USERNAME);
        /*TTs - text to speech*/
        webhook.setTts(false);
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
