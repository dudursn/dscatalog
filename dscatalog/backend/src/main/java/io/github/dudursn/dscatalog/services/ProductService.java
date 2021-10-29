package io.github.dudursn.dscatalog.services;

import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.dtos.ProductDTO;
import io.github.dudursn.dscatalog.entities.Category;
import io.github.dudursn.dscatalog.entities.Product;
import io.github.dudursn.dscatalog.repositories.CategoryRepository;
import io.github.dudursn.dscatalog.repositories.ProductRepository;
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
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DiscordWebHookClient webHookClient;

    //private Gson gson = new Gson();

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(){
        List<Product> result = repository.findAll();
        return result.stream().map(data -> new ProductDTO(data)).collect(Collectors.toList());

        /*
        Modo Trabalhoso
            List<ProductDTO> listDTO = new ArrayList();
            for (Product cat : list){
                listDTO.add(new ProductDTO(cat));
            }
            return listDTO;
         */
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow( () -> new ResourceNotFoundException("Data not found"));
        return new ProductDTO(entity);
    }

    @Transactional(readOnly = true)
    public ProductDTO findByIdAndCategories(long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow( () -> new ResourceNotFoundException("Data not found"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO save(ProductDTO dto) {
        Product entity = new Product();

        entity = this.copyDtoToEntity(dto, entity);

        entity = repository.save(entity);
        webHookClient.execute(
                "Insert new Product Id: " + entity.getId() + ", Time: " + Instant.now().toString());
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {

        try {

            Product entity = repository.getById(id);
            entity = this.copyDtoToEntity(dto, entity);
            entity.setId(id);
            entity = repository.save(entity);

            webHookClient.execute(
                    "Update Product Id: " + id + ", Time: " + Instant.now().toString());

            return new ProductDTO(entity);
        }catch (EntityNotFoundException e){
            webHookClient.execute(
                    "Error - Update Product Id: " + id + ", Time: " + Instant.now().toString());
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }
    }

    public void delete(long id) {

        try {

            repository.deleteById(id);
            webHookClient.execute(
                    "Delete Product Id: " + id + ", Time: " + Instant.now().toString());

        }catch (EmptyResultDataAccessException e){
            webHookClient.execute(
                    "Error - Delete Product Id: " + id + ", Time: " + Instant.now().toString()+ ", Not found");
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }catch (DataIntegrityViolationException e){
            webHookClient.execute(
                    "Error - Delete Product Id: " + id + ", Time: " + Instant.now().toString()+ ", Integrity violation");
            throw new DataBaseException("Integrity violation");
        }
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPagination(Pageable pageable) {

        Page<Product> result = repository.findAll(pageable);
        //Cada elemento da lista original é convertida para a classe alvo DTO e
        // depois a stream é convertida para lista
        return result.map(data -> new ProductDTO(data));
    }

    private Product copyDtoToEntity(ProductDTO dto, Product entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for(CategoryDTO catDto : dto.getCategories()) {
            Category cat = categoryRepository.getById(catDto.getId());
            entity.getCategories().add(cat);
        };

        return entity;
    }
}
