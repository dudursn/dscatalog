package io.github.dudursn.dscatalog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class IndexController {
    @GetMapping
    public ResponseEntity<String> index(){

        return new ResponseEntity<>("The application is running!", HttpStatus.OK);
    }
}
