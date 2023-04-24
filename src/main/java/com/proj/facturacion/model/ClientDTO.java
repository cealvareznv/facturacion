package com.proj.facturacion.model;

import com.proj.facturacion.constraint.ClientDniConstraint;
import com.proj.facturacion.constraint.ClientNombreApellidoConstraint;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ClientDTO implements Serializable {
    private Long id;
    @ClientNombreApellidoConstraint
    private String nombre;
    @ClientNombreApellidoConstraint
    private String apellido;
    @ClientDniConstraint
    private String dni;

    //Getter And Setter
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = FormatterDni(dni);
    }

    private static String FormatterDni(String docnumber){
        Long partDni = Long.valueOf(docnumber.replaceAll("([^0-9])",""));
        String partCodeBefore = docnumber.replaceAll("[0-9].*$","");
        String partCodeAfter = docnumber.replaceAll("^.*[0-9]","");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String formatterDni = formatter.format(partDni);
        String newDocNumber = partCodeBefore+(formatterDni.replace("\\,","\\."))+partCodeAfter;
        return (newDocNumber.trim());
    }
}
