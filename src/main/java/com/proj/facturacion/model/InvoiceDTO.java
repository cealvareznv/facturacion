package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
//Clase que permite interacturar con la entidad Invoice para la transferencia de datos
public class InvoiceDTO implements Serializable {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private String fec_created;
    private Double total;
    private Client client;
    private List<InvoiceDetailsDTO> invoiceDetailsDTO;
}
