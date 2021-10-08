package io.github.dudursn.dscatalog.services;

import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.entities.Category;
import io.github.dudursn.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryDTO> findAll(){
        List<Category> result = repository.findAll();
        return result.stream().map(data -> new CategoryDTO(data)).collect(Collectors.toList());
    }
}
