package com.proj.facturacion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDAO {
    private Long id;
    private String fec_created;
    private Double total;
    private Client client;
}
