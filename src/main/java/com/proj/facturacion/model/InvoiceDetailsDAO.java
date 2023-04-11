package com.proj.facturacion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDetailsDAO {
    private Long invoiceDetailId;
    private Integer amoun;
    private Double price;
    private Product product;
    private Invoice invoice;
}
