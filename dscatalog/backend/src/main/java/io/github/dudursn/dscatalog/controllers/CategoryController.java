package io.github.dudursn.dscatalog.controllers;

import io.github.dudursn.dscatalog.dtos.CategoryDTO;
import io.github.dudursn.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") long id){

        CategoryDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO dto){

        dto = service.save(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        //Resource's Status Created => 201
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/save/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") long id, @RequestBody CategoryDTO dto){

        dto = service.update(dto, id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id){

        service.delete(id);
        //Method delete => no body and status code 204
        return ResponseEntity.noContent().build();
    }
}
