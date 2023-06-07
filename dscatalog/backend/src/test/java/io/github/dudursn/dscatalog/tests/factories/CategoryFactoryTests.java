package io.github.dudursn.dscatalog.tests.factories;

import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.entities.Category;


public class CategoryFactoryTests {

    public static Category createCategory(){
        Category category = new Category(2L, "Electronics");
        return category;
    }

    public static CategoryDTO createCategoryDTO(){
        Category category = createCategory();
        CategoryDTO categoryDTO = new CategoryDTO(category);
        return categoryDTO;
    }
}
