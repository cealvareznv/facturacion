package com.proj.facturacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import java.lang.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "clients")
//Anotación utilizada para hacer un soft delete en la Entidad Cliente
@SQLDelete(sql="UPDATE clients SET deleted = true WHERE id=?")
@FilterDef(name = "deletedClientFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedClientFilter", condition = "deleted = :isDeleted")
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
    //Se implementa este atributo para realizar un Soft Delete en la tabla cliente
    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
    //Se mapea la relación con el objeto Invoice a través del atributo clients
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Invoice> invoices = new ArrayList<>();
}
