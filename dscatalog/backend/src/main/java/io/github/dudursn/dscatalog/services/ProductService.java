package io.github.dudursn.dscatalog.services;

import io.github.dudursn.dscatalog.constants.TypeMessage;
import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.dtos.ProductDTO;
import io.github.dudursn.dscatalog.entities.Category;
import io.github.dudursn.dscatalog.entities.Product;
import io.github.dudursn.dscatalog.repositories.CategoryRepository;
import io.github.dudursn.dscatalog.repositories.ProductRepository;
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
    private NotifyDiscordService notifyDiscordService;

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
        notifyDiscordService.notify(TypeMessage.INSERT, entity, "");
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {

        try {

            Product entity = repository.getById(id);
            entity = this.copyDtoToEntity(dto, entity);
            entity.setId(id);
            entity = repository.save(entity);

            notifyDiscordService.notify(TypeMessage.UPDATE, entity, "");

            return new ProductDTO(entity);
        }catch (EntityNotFoundException e){
            notifyDiscordService.notify(TypeMessage.ERROR, id, "Update: " + e.getMessage() );
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }
    }

    public void delete(long id) {

        try {

            repository.deleteById(id);
            notifyDiscordService.notify(TypeMessage.DELETE, id, "");

        }catch (EmptyResultDataAccessException e){

            notifyDiscordService.notify(TypeMessage.ERROR, id, "Delete: " + e.getMessage() );
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }catch (DataIntegrityViolationException e){
            notifyDiscordService.notify(TypeMessage.ERROR, id, "Delete: " + e.getMessage() );
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

    @Transactional(readOnly = true)
    public long getTotalCountProduct(){
        try{

            return repository.count();
        }catch (Exception e){

            throw new ResourceNotFoundException("Table isn't exist");
        }
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
