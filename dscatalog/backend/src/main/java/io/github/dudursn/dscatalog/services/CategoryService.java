package io.github.dudursn.dscatalog.services;

import com.google.gson.Gson;
import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.entities.Category;
import io.github.dudursn.dscatalog.repositories.CategoryRepository;
import io.github.dudursn.dscatalog.services.discord.DiscordWebHookClient;
import io.github.dudursn.dscatalog.services.exceptions.DataBaseException;
import io.github.dudursn.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private DiscordWebHookClient webHookClient;

    //private Gson gson = new Gson();

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
        Category entity = obj.orElseThrow( () -> new ResourceNotFoundException("Data not found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        webHookClient.execute(
                "Insert new Category Id: " + entity.getId() + ", Time: " + Instant.now().toString());
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(CategoryDTO dto, Long id) {

        try {
            Category entity = repository.getById(id);
            entity.setId(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);

            webHookClient.execute(
                    "Update Category Id: " + id + ", Time: " + Instant.now().toString());

            return new CategoryDTO(entity);
        }catch (EntityNotFoundException e){
            webHookClient.execute(
                    "Error - Update Category Id: " + id + ", Time: " + Instant.now().toString());
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }
    }

    public void delete(long id) {

        try {

            repository.deleteById(id);
            webHookClient.execute(
                    "Delete Category Id: " + id + ", Time: " + Instant.now().toString());

        }catch (EmptyResultDataAccessException e){
            webHookClient.execute(
                    "Error - Delete Category Id: " + id + ", Time: " + Instant.now().toString()+ ", Not found");
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }catch (DataIntegrityViolationException e){
            webHookClient.execute(
                    "Error - Delete Category Id: " + id + ", Time: " + Instant.now().toString()+ ", Integrity violation");
            throw new DataBaseException("Integrity violation");
        }
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPagination(Pageable pageable) {

        Page<Category> result = repository.findAll(pageable);
        //Cada elemento da lista original é convertida para a classe alvo DTO e
        // depois a stream é convertida para lista
        return result.map(data -> new CategoryDTO(data));
    }
}
