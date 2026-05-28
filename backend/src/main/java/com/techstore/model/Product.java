package com.techstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String image;
    private String category;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer stock;
    private Double rating;
    private Integer sales;
}
