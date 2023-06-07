package io.github.dudursn.dscatalog.tests.services;

import io.github.dudursn.dscatalog.constants.TypeMessage;
import io.github.dudursn.dscatalog.repositories.ProductRepository;
import io.github.dudursn.dscatalog.services.NotifyDiscordService;
import io.github.dudursn.dscatalog.services.ProductService;
import io.github.dudursn.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;


//Teste de unidade
@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private NotifyDiscordService notifyDiscordService;

    private long existingId, notExistingId;

    @BeforeEach
    void setUp() throws Exception{
        System.out.println("Running Unitary Tests.");
        existingId = 1L;
        notExistingId = 1000L;

        /*Configuração dos comportamentos nos mockitos para impedir a execução
        dos métodos e fazer o teste de unidade*/
        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doNothing().when(notifyDiscordService).notify(TypeMessage.DELETE, existingId, "");
        Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(notExistingId);
        Mockito.doNothing().when(notifyDiscordService).notify(TypeMessage.ERROR, notExistingId, "");
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){


        Assertions.assertDoesNotThrow( () -> {
            service.delete(existingId);
        });

        // used to check that certain behavior happened.
        Mockito.verify(repository).deleteById(existingId);
        Mockito.verify(notifyDiscordService).notify(TypeMessage.DELETE, existingId, "");
        /*
        Mockito.verify(repository, Mockito.times(1).deleteById(existingId);
        Mockito.verify(repository, Mockito.never().deleteById(existingId);
         */
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){

        Assertions.assertThrows( ResourceNotFoundException.class, () -> {
            service.delete(notExistingId);
        });

        // used to check that certain behavior happened.
        Mockito.verify(repository).deleteById(notExistingId);

    }

}
