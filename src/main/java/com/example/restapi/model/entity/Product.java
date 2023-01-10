package com.example.restapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_product")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Product implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Column(name = "product_name" , length = 100)
    private String name;

    @NotEmpty(message = "Description is required")
    @Column(name = "product_desc" , length = 500)
    private String description;
    private Double price;

    @ManyToOne  //banyak product punya 1 kategory
    private  Category category;

    @ManyToMany //satu produk punya banyak supplier
    @JoinTable(name = "tbl_product_supplier",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "supplier_id"))
//    @JsonManagedReference
    private Set<Supplier> suppliers;
}
