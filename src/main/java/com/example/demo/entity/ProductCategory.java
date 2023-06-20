package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "product_category")
@Getter
@Setter
public class ProductCategory {
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="category_name")
    private String categoryName;
    @OneToMany
    @JoinColumn(name = "category_id")
    private Set<Product> products ;
}

