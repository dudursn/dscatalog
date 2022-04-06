package io.github.dudursn.dscatalog.tests.repositories;

import io.github.dudursn.dscatalog.dtos.ProductDTO;
import io.github.dudursn.dscatalog.entities.Product;
import io.github.dudursn.dscatalog.repositories.ProductRepository;
import io.github.dudursn.dscatalog.tests.factories.ProductFactoryTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long id;
    private long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("Running Junit Test Suite.");
        id = 1L;
        nonExistingId = 1000L;

    }


    @Test
    public void deleteShouldDeleteProductWhenIdExists(){

        repository.deleteById(id);
        Optional<Product> result = repository.findById(id);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists(){
        Optional<Product> result = repository.findById(id);
        Assertions.assertNotEquals(result, Optional.empty());
    }

    @Test
    public void findByIdShouldReturnEmptyWhenIdNotExists(){
        Optional<Product> result = repository.findById(nonExistingId);
        Assertions.assertEquals(result, Optional.empty());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){

        long totalCount = repository.count();
        Product product = ProductFactoryTests.createProduct();
        product.setId(null);
        product = repository.save(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(totalCount+1, product.getId());
    }

    @Test
    public void deleteProductShouldThrowEmptyResultDataAccessExceptionWhenIdNotExists(){

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });

    }

    @Test
    public void listAllProductsWhenExist(){
        List<ProductDTO> listDTO = new ArrayList<>();
        List<Product> result  = repository.findAll();
        result.forEach(data -> {
            System.out.println(data.toString());
            listDTO.add(new ProductDTO(data));
        });
    }
}
