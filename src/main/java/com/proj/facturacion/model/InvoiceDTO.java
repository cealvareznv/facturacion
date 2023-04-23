package com.proj.facturacion.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//Clase que permite interacturar con la entidad Invoice para la transferencia de datos
public class InvoiceDTO {
    private Long id;
    private String fec_created;
    private Double total;
    private Client client;
    private List<InvoiceDetailsDTO> invoiceDetailsDTO;
}
