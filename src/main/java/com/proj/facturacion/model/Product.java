package com.proj.facturacion.model;

import com.proj.facturacion.constraint.ProductCodeConstraint;
import com.proj.facturacion.constraint.ProductPriceConstraint;
import com.proj.facturacion.constraint.ProductStockConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    //Se establece la clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La descripción no es valida. El producto debe de tener una descripción.")
    @Column(name = "description", length = 150)
    private String description;
    @ProductCodeConstraint
    @Column(name = "code", length = 50, unique = true)
    private String code;
    @ProductStockConstraint
    private Integer stock;
    @ProductPriceConstraint
    private Double price;
}
