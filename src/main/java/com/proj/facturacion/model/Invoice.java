package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.lang.*;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se establece la clave primaria
    private Long id;
    @Column(name = "created_at")
    private String fec_created;
    private Double total;
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id") //Se establece la relación con el objeto Clients
    @JsonIgnore
    private Clients clients;
    @OneToMany(mappedBy = "invoice",fetch = FetchType.LAZY, cascade = CascadeType.ALL) //Se establece la relación con el objeto InvoiceDetails
    @JsonIgnore
    private List<InvoiceDetails> invoiceDetails;
}
