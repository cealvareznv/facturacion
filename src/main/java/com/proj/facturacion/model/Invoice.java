package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.lang.*;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice {
    //Se establece la clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at")
    private String fec_created;
    private Double total;
    //Se establece la relación con el objeto Clients
    @ManyToOne
    @JoinColumn(name = "client_id")
    //@JsonIgnore
    private Client client;
    //Se establece la relación con el objeto InvoiceDetails a través del atributo invoice
    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvoiceDetails> invoiceDetails;
}
