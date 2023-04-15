package com.proj.facturacion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Clase que permite interacturar con la entidad InvoiceDetails para manejar datos en el motodo POST
public class InvoiceDetailsDAO {
    private Long invoiceDetailId;
    private Integer amoun;
    private Double price;
    private Product product;
    private Invoice invoice;
}
