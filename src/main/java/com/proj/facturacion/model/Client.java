package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    //Se establece la clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 75)
    private String nombre;
    @Column(name = "lastname", length = 75)
    private String apellido;
    //Se establece un contrain unique
    @Column(name = "docnumber", length = 11, unique = true)
    private String dni;
    //Se mapea la relación con el objeto Invoice a través del atributo clients
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Invoice> invoices = new ArrayList<>();
}
