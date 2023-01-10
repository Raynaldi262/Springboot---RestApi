package com.example.restapi.controller;

import com.example.restapi.dto.CategoryData;
import com.example.restapi.dto.ResponseData;
import com.example.restapi.dto.SearchData;
import com.example.restapi.dto.SupplierData;
import com.example.restapi.model.entity.Category;
import com.example.restapi.model.entity.Supplier;
import com.example.restapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryData categoryData, Errors errors){
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

//        Supplier supplier = Supplier.builder()
//                .name(supplierData.getName())
//                .address(supplierData.getAddress())
//                .email(supplierData.getEmail())
//                .build();

        Category category = modelMapper.map(categoryData , Category.class);

        responseData.setStatus(true);
        responseData.setPayload(categoryService.save(category));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Category> findAll(){
        return categoryService.findAllCategory();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable("id") Long id){
        return categoryService.findCategoryById(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Category>> update(@Valid @RequestBody CategoryData categoryData, Errors errors){
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

//        Supplier supplier = Supplier.builder()
//                .name(supplierData.getName())
//                .address(supplierData.getAddress())
//                .email(supplierData.getEmail())
//                .build();

        Category category = modelMapper.map(categoryData , Category.class);

        responseData.setStatus(true);
        responseData.setPayload(categoryService.save(category));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/{size}/{page}")
    public Iterable<Category> findByName(@RequestBody SearchData searchData, @PathVariable("size") int size,
        @PathVariable("page") int page){
        Pageable pageable = PageRequest.of(page,size);
        return categoryService.findByName(searchData.getSearchKey(), pageable);
    }

    @PostMapping("/search/{size}/{page}/{sort}")
    public Iterable<Category> findByName(@RequestBody SearchData searchData, @PathVariable("size") int size,
                                         @PathVariable("page") int page, @PathVariable("sort") String sort){
        Pageable pageable = PageRequest.of(page,size, Sort.by("id").ascending());
        if(sort.equalsIgnoreCase("desc")){
            pageable = PageRequest.of(page,size, Sort.by("id").descending());
        }
        
        return categoryService.findByName(searchData.getSearchKey(), pageable);
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseData<Iterable<Category>>> createBatch(@RequestBody Category[] categories){
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setPayload(categoryService.saveBatch(Arrays.asList(categories)));
        responseData.setStatus(true);
        return ResponseEntity.ok(responseData);
    }
}
