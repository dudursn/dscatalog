package io.github.dudursn.dscatalog.controllers;


import io.github.dudursn.dscatalog.dtos.ProductDTO;
import io.github.dudursn.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){

        List<ProductDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value="/pagination")
    public ResponseEntity<Page<ProductDTO>> findAllPagination(Pageable pageable){
        Page<ProductDTO> list = service.findAllPagination(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") long id){

        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}/categories")
    public ResponseEntity<ProductDTO> findByIdAndCategories(@PathVariable("id") long id){

        ProductDTO dto = service.findByIdAndCategories(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO dto){

        dto = service.save(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        //webHookClient.execute(ResponseEntity.created(uri).body(dto).toString());

        //Resource's Status Created => 201
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/save/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") long id, @RequestBody ProductDTO dto){

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
