package com.example.restapi.controller;

import com.example.restapi.dto.ResponseData;
import com.example.restapi.dto.SearchData;
import com.example.restapi.dto.SupplierData;
import com.example.restapi.model.entity.Supplier;
import com.example.restapi.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierData supplierData, Errors errors){
        ResponseData<Supplier> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                System.err.println(error.getDefaultMessage());
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = Supplier.builder()
                .name(supplierData.getName())
                .address(supplierData.getAddress())
                .email(supplierData.getEmail())
                .build();

//        Supplier supplier = modelMapper.map(supplierData , Supplier.class);
        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Supplier> findAll(){
        return supplierService.findAllSupplier();
    }

    @GetMapping("/{id}")
    public Supplier findById(@PathVariable("id") Long id){
        return supplierService.findSupplierById(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Supplier>> update(@Valid @RequestBody SupplierData supplierData, Errors errors){
        ResponseData<Supplier> responseData = new ResponseData<>();
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

        Supplier supplier = modelMapper.map(supplierData , Supplier.class);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("search/email")
    public Supplier findByEmail(@RequestBody SearchData searchData){
        return supplierService.findByEmail(searchData.getSearchKey());
    }

    @PostMapping("search/name")
    public List<Supplier> findByName(@RequestBody SearchData searchData){
        return supplierService.findByNameContains(searchData.getSearchKey());
    }

    @PostMapping("search/namestart")
    public List<Supplier> findByNameStartWith(@RequestBody SearchData searchData){
        return supplierService.findByNameStartWith(searchData.getSearchKey());
    }

    @PostMapping("search/nameoremail")
    public List<Supplier> findByNameOrEmail(@RequestBody SearchData searchData){
        return supplierService.findByNameOrEmail(searchData.getSearchKey(), searchData.getOtherSearchKey());
    }


}
