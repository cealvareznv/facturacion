package com.proj.facturacion.model;

import lombok.Data;
@Data
public class InvoiceDAO {
    private Long id;
    private String fec_created;
    private Double total;
    private Clients clients;
}
