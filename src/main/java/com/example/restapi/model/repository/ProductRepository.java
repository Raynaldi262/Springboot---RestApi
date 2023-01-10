package com.example.restapi.model.repository;

import com.example.restapi.model.entity.Product;
import com.example.restapi.model.entity.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    //Crud repository paling simple
    //Page and source repository
    //JPA repository lebih lengkap

    List<Product> findByNameContains(String name);

    // penamaan kolom pada query berdasarkan object bukan table
    @Query("Select p from Product p where p.name =  :name")
    Product findProductByPName(@PathParam("name") String name);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    List<Product> findProductByCategoryId(@PathParam("id") Long id);

    @Query("SELECT p FROM Product p WHERE :supplier MEMBER OF p.suppliers")
    List<Product> findBySupplier(@PathParam("supplier") Supplier supplier);
}
