package io.github.dudursn.dscatalog.services;

import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.entities.Category;
import io.github.dudursn.dscatalog.repositories.CategoryRepository;
import io.github.dudursn.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> result = repository.findAll();
        return result.stream().map(data -> new CategoryDTO(data)).collect(Collectors.toList());

        /*
        Modo Trabalhoso
            List<CategoryDTO> listDTO = new ArrayList();
            for (Category cat : list){
                listDTO.add(new CategoryDTO(cat));
            }
            return listDTO;
         */
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow( () -> new EntityNotFoundException("Data not found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }
}
