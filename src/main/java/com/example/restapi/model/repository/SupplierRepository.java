package com.example.restapi.model.repository;

import com.example.restapi.model.entity.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierRepository extends CrudRepository<Supplier,Long> {

    //https://docs.spring.io/spring-data/jpa/docs/2.7.5/reference/html/#appendix.query.method.subject

    Supplier findByEmail(String  email);
    List<Supplier> findByNameContainsOrderByIdDesc(String name); // like dengan % depan belakang

    List<Supplier> findByNameStartingWith(String prefix); //pencariian diawali kata apa

    List<Supplier> findByNameContainsOrEmailContains(String name, String email);
}
