package io.github.dudursn.dscatalog.dtos;

import io.github.dudursn.dscatalog.entities.Category;
public class CategoryDTO {

    public Long id;
    public String name;

    public CategoryDTO(){}

    public CategoryDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
