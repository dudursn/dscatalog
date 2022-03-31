package io.github.dudursn.dscatalog.factories;

import io.github.dudursn.dscatalog.entities.Category;

public class CategoryFactoryTests {

    public static Category createCategory(){
        Category category = new Category(2L, "Electronics");
        return category;
    }
}
