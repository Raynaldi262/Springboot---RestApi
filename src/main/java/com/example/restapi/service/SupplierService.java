package com.example.restapi.service;

import com.example.restapi.model.entity.Supplier;
import com.example.restapi.model.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public Supplier findSupplierById(Long id){
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if(!supplier.isPresent()){
            return null;
        }
        return supplier.get();
    }

    public  Iterable<Supplier> findAllSupplier(){
        return supplierRepository.findAll();
    }

    public void deleteSupplier(Long id){
        supplierRepository.deleteById(id);
    }

    public Supplier findByEmail(String email){
        return supplierRepository.findByEmail(email);
    }

    public List<Supplier> findByNameContains(String name){
        return supplierRepository.findByNameContainsOrderByIdDesc(name);
    }

    public List<Supplier> findByNameStartWith(String prefix){
        return supplierRepository.findByNameStartingWith(prefix);
    }

    public List<Supplier> findByNameOrEmail(String name, String email){
        return supplierRepository.findByNameContainsOrEmailContains(name, email);
    }
}
