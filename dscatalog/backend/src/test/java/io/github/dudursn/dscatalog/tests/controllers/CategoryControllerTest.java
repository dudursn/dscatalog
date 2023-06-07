package io.github.dudursn.dscatalog.tests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.dudursn.dscatalog.controllers.CategoryController;
import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.services.CategoryService;

import io.github.dudursn.dscatalog.tests.factories.CategoryFactoryTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = CategoryController.class)
//Para não se preocupar com tokens
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    //O mapper vai ser plugado no mockmvc para que nós nãop temos que mapear
    // os objetos retornados pelo endoint da api.
    @Autowired
    private ObjectMapper mapper;

    private CategoryDTO dto;

    @BeforeEach
    void setUp() throws Exception{
        System.out.println("Running Unit Tests.");
        dto = CategoryFactoryTests.createCategoryDTO();
    }

    @Test
    public void returnsCreatedWhenSaveACategory() throws Exception {
        /*Configuração dos comportamentos no mockito para impedir a execução
        do método para fazer o teste de unidade*/
        BDDMockito.given(categoryService.save(ArgumentMatchers.any())).willAnswer(invocation ->
                invocation.getArgument(0));

        //Simula uma requisição para um endpoint assim como a resposta retornada.
        ResultActions response = mockMvc.perform(post("/categories/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)));

        //Verifica a resposta esperada
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        //Usado para checar se o comportamento aconteceu
        BDDMockito.verify(categoryService).save(ArgumentMatchers.any());
    }

    @Test
    public void returnsNoContentWhenDeleteACategory() throws Exception {
        /* Configuração dos comportamentos no mockito para impedir a execução
        do método para fazer o teste de unidade */
        BDDMockito.doNothing().when(categoryService).delete(ArgumentMatchers.anyLong());

        //Simula uma requisição para um endpoint assim como a resposta retornada.
        ResultActions response = mockMvc.perform(delete("/categories/delete/" + dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));

        //Verifica a resposta esperada
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(""));

        //Usado para checar se o comportamento aconteceu
        BDDMockito.verify(categoryService).delete(ArgumentMatchers.anyLong());
    }

    @Test
    public void returnsOkWhenUpdateACategory() throws Exception {
      /*Configuração dos comportamentos no mockito para impedir a execução
        do método para fazer o teste de unidade*/
        BDDMockito.given(categoryService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).willAnswer(
                invocation -> invocation.getArgument(0)
        );

        //Simula uma requisição para um endpoint assim como a resposta retornada.
        ResultActions response = mockMvc.perform(put("/categories/save/" + dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)));

        //Verifica a resposta esperada
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        //Usado para checar se o comportamento aconteceu
        BDDMockito.verify(categoryService).update(ArgumentMatchers.any(), ArgumentMatchers.anyLong());
    }
}
