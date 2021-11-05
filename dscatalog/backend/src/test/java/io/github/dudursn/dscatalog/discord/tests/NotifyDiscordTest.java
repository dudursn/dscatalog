package io.github.dudursn.dscatalog.discord.tests;


import io.github.dudursn.dscatalog.constants.TypeMessage;
import io.github.dudursn.dscatalog.entities.Category;
import io.github.dudursn.dscatalog.entities.Product;
import io.github.dudursn.dscatalog.services.NotifyDiscordService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotifyDiscordTest {

    @Autowired
    NotifyDiscordService notifyDiscordService;

    @Test
    public void eventNotifyBotDiscordInsert(){
        //Arrange
        Product p = new Product(100L, "name", "String description", 1.73, "String imgUrl");
        Category c = new Category(555L, "Test");

        //Action
        notifyDiscordService.notify(TypeMessage.INSERT, c, "");
    }

    @Test
    public void eventNotifyBotDiscordUpdate(){
        //Arrange
        Product p = new Product(100L, "name", "String description", 1.73, "String imgUrl");
        Category c = new Category(555L, "Test");

        //Action
        notifyDiscordService.notify(TypeMessage.UPDATE, c, "");
    }

    @Test
    public void eventNotifyBotDiscordDelete(){

        //Action
        notifyDiscordService.notify(TypeMessage.DELETE, 2L, "");
    }

    @Test
    public void eventNotifyBotDiscordError(){

        //Action
        notifyDiscordService.notify(TypeMessage.ERROR, 2L, "UPDATE ERROR");
    }
}
