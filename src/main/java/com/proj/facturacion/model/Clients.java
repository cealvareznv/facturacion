package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.lang.*;
import java.util.*;

@Data
@Entity
@Table(name = "clients")
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se establece la clave primaria
    private Long id;
    @Column(name = "name", length = 75)
    private String nombre;
    @Column(name = "lastname", length = 75)
    private String apellido;
    @Column(name = "docnumber", length = 11, unique = true) //Se establece un contrain unique
    private String dni;
    @OneToMany(mappedBy = "clients", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //Se mapea la realción con el objeto Invoice
    @JsonManagedReference
    @JsonIgnore
    private List<Invoice> invoices = new ArrayList<>();
}
