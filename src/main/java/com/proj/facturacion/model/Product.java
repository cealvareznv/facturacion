package com.proj.facturacion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    //Se establece la clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", length = 150)
    private String description;
    @Column(name = "code", length = 50, unique = true)
    private String code;
    private Integer stock;
    private Double price;
}
