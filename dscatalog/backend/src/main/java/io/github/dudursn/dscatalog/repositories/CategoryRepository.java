package io.github.dudursn.dscatalog.repositories;

import io.github.dudursn.dscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
