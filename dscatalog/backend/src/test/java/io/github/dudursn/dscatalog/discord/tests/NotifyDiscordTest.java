package io.github.dudursn.dscatalog.discord.tests;


import io.github.dudursn.dscatalog.constants.TypeMessage;
import io.github.dudursn.dscatalog.entities.Category;
import io.github.dudursn.dscatalog.entities.Product;
import io.github.dudursn.dscatalog.factories.CategoryFactoryTests;
import io.github.dudursn.dscatalog.factories.ProductFactoryTests;
import io.github.dudursn.dscatalog.services.NotifyDiscordService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotifyDiscordTest {

    @Autowired
    NotifyDiscordService notifyDiscordService;

    private Product product;
    private Category category;
    private long idTesteDeleteUpdate;

    @BeforeEach
    void setUp() throws Exception {

        //Arrange
        product = ProductFactoryTests.createProduct();
        category = CategoryFactoryTests.createCategory();
        idTesteDeleteUpdate = 2L;
    }

    @Test
    public void eventNotifyBotDiscordInsert(){

        //Action
        notifyDiscordService.notify(TypeMessage.INSERT, category, "");
    }

    @Test
    public void eventNotifyBotDiscordUpdate(){

        //Action
        notifyDiscordService.notify(TypeMessage.UPDATE, category, "");
    }

    @Test
    public void eventNotifyBotDiscordDelete(){

        //Action
        notifyDiscordService.notify(TypeMessage.DELETE, idTesteDeleteUpdate, "");
    }

    @Test
    public void eventNotifyBotDiscordError(){

        //Action
        notifyDiscordService.notify(TypeMessage.ERROR, idTesteDeleteUpdate, "UPDATE ERROR");
    }
}
