package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice_details")
public class InvoiceDetails {
    //Se establece la clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_detail_id")
    private Long invoiceDetailId;
    private Integer amoun;
    private Double price;
    //Se establece la relación con el objeto Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
    //Se establece la relación con el objeto Invoice
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;
}
