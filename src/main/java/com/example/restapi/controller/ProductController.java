package com.example.restapi.controller;


import com.example.restapi.dto.ProductDto;
import com.example.restapi.dto.ResponseData;
import com.example.restapi.dto.SearchData;
import com.example.restapi.dto.SupplierData;
import com.example.restapi.model.entity.Product;
import com.example.restapi.model.entity.Supplier;
import com.example.restapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/products")
@ControllerAdvice
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody Product product, Errors errors){

        ResponseData<Product> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError err: errors.getAllErrors()) {
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Product> findAll(){
        return productService.findAllProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ProductDto>> findById(@PathVariable("id") Long id){
        ResponseData<ProductDto> responseData = new ResponseData<>();
        ProductDto product = productService.findByProductId(id);
        if(!product.equals(null)){
            responseData.setStatus(true);
            responseData.setPayload(product);
            responseData.getMessage().add("berhasil");
        }else{
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessage().add("Gagal");
        }

        return ResponseEntity.ok(responseData)  ;
    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> UpdateProduct(@Valid @RequestBody Product product , Errors errors){
        ResponseData<Product> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError err: errors.getAllErrors()) {
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);

    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteById(id);
    }

    @PostMapping("/{id}")
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long productID){
        productService.addSupplier(supplier, productID);
    }

    @PostMapping("/search/name")
    public Product getProductName(@RequestBody SearchData searchData){
        return productService.findByProductName(searchData.getSearchKey());
    }

    @GetMapping("search/{id}")
    public List<Product> getProductByCategoryId(@PathVariable("id") Long id){
        return productService.findByCategoryId(id);
    }

    @GetMapping("search/supplier/{id}")
    public List<Product> getProductBySupplierId(@PathVariable("id") Long id){
        return productService.findBySupplier(id);
    }
    
    
}

