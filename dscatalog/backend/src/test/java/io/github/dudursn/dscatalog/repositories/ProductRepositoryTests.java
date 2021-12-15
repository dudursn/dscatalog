package io.github.dudursn.dscatalog.repositories;

import io.github.dudursn.dscatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long id;
    private long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {

        id = 1L;
        nonExistingId = 1000L;
    }


    @Test
    public void deleteProductWhenIdExists(){

        repository.deleteById(id);
        Optional<Product> result = repository.findById(id);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteProductShouldThrowEmptyResultDataAccessExceptionWhenIdNotExists(){

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });

    }
}
