package com.example.restapi.model.repository;

import com.example.restapi.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Page<Category> findByNameContains(String name, Pageable pageable);
}
