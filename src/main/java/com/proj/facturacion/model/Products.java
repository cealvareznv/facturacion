package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se establece la clave primaria
    private Long id;
    @Column(name = "description", length = 150)
    private String description;
    @Column(name = "code", length = 50, unique = true)
    private String code;
    private Integer stock;
    private Double price;
    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL) //Se mapea la realción con el objeto InvoiceDetails
    @JsonManagedReference
    @JsonIgnore
    private List<InvoiceDetails> invoiceDetails;
}
