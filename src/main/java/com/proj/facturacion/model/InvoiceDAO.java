package com.proj.facturacion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Clase que permite interacturar con la entidad Invoice para manejar datos en el motodo POST
public class InvoiceDAO {
    private Long id;
    private String fec_created;
    private Double total;
    private Client client;
}
