package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
//Clase que permite interacturar con la entidad InvoiceDetails para la transferencia de datos
public class InvoiceDetailsDTO implements Serializable {
    private Long invoiceDetailId;
    private Integer amoun;
    private Double price;
    private ProductDTO productDTO;
}
