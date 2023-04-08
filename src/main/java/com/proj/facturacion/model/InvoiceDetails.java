package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice_details")
public class InvoiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se establece la clave primaria
    @Column(name = "invoice_detail_id")
    private Long invoiceDetailId;
    private Integer amoun;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "product_id") //Se establece la relación con el objeto Products
    @JsonBackReference
    private Products products;
    @ManyToOne
    @JoinColumn(name = "invoice_id") //Se establece la relación con el objeto Invoice
    @JsonBackReference
    private Invoice invoice;
}
