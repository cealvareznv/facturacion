package com.proj.facturacion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Clase que permite interacturar con la entidad InvoiceDetails para la transferencia de datos
public class InvoiceDetailsDTO {
    private Long invoiceDetailId;
    private Integer amoun;
    private Double price;
    private Product product;
}
