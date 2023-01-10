package com.example.restapi.service;

import com.example.restapi.model.entity.Category;
import com.example.restapi.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save (Category category){
        if(category.getId()!=null){
            Category currCategory = categoryRepository.findById(category.getId()).get();
            currCategory.setName(category.getName());
            category = currCategory;
        }
        return categoryRepository.save(category);
    }

    public Category findCategoryById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()){
            return null;
        }

        return category.get();
    }

    public Iterable<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }

    public Iterable<Category> findByName(String name, Pageable pageable){
        return categoryRepository.findByNameContains(name, pageable);
    }

    public Iterable<Category> saveBatch(Iterable<Category> categories){
        return categoryRepository.saveAll(categories);
    }
}
