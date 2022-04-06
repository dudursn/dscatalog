package io.github.dudursn.dscatalog.tests.factories;

import io.github.dudursn.dscatalog.dtos.ProductDTO;
import io.github.dudursn.dscatalog.entities.Product;

import java.time.Instant;

public class ProductFactoryTests {

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Good Phone", 800.0,
                "https://img.teste.com/img.png", Instant.parse("2022-03-31T11:14:00Z"));
        product.getCategories().add(CategoryFactoryTests.createCategory());
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        ProductDTO productDTO = new ProductDTO(product, product.getCategories());
        return productDTO;
    }
}
