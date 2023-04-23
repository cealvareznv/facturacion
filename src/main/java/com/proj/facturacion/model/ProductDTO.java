package com.proj.facturacion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String description;
    private String code;
    private Integer stock;
    private Double price;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                '}';
    }
}
