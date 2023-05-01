package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
//Clase que permite interacturar con la entidad Product para la transferencia de datos
public class ProductDTO implements Serializable {
    private Long id;
    private String description;
    private String code;
    @JsonIgnore
    private Integer stock;
    private Double price;
}
