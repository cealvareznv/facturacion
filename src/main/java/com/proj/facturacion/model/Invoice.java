package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
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
    private Date fec_created;
    private Double total;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false) //Se establece la relación con el objeto Clients
    @JsonBackReference
    private Clients clients;
    @OneToMany(mappedBy = "invoice",fetch = FetchType.LAZY, cascade = CascadeType.ALL) //Se establece la relación con el objeto InvoiceDetails
    @JsonManagedReference
    private List<InvoiceDetails> invoiceDetails;
}
