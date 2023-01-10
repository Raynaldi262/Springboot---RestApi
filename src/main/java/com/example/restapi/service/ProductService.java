package com.example.restapi.service;

import com.example.restapi.dto.ProductDto;
import com.example.restapi.exception.BadRequestException;
import com.example.restapi.model.entity.Product;
import com.example.restapi.model.entity.Supplier;
import com.example.restapi.model.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SupplierService supplierService;

    public Product save(Product product){
        return productRepository.save(product);
    }

    public ProductDto findByProductId(Long id){
        Product product = productRepository.findById(id)
                                        .orElseThrow(() -> new BadRequestException("Delete gagal, Produk tidak ditemukan"));

        ProductDto productDto =  modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    public Iterable<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public void deleteById(Long id){
        Product product = productRepository.findById(id)
                                .orElseThrow(() -> new BadRequestException("Delete gagal, Produk tidak ditemukan"));
        productRepository.deleteById(id);
    }

    public List<Product> findByName(String name){

        return productRepository.findByNameContains(name);
    }

    public void addSupplier(Supplier supplier, Long productID){
//        Product product = findByProductId(productID);
        Product product = productRepository.findById(productID).get();
        if(product == null){
            throw new RuntimeException("Product with ID "+product+" not found");
        }
        product.getSuppliers().add(supplier);
        save(product);
    }

    public Product findByProductName(String name){
        return productRepository.findProductByPName(name);
    }

    public List<Product> findByCategoryId(Long id){
        return productRepository.findProductByCategoryId(id);
    }

    public List<Product> findBySupplier(Long supplierId){
        Supplier supplier = supplierService.findSupplierById(supplierId);
        if(supplier == null){
            return new ArrayList<Product>();
        }
        return productRepository.findBySupplier(supplier);
    }
}
