package com.proj.facturacion.exception;

import org.springframework.http.HttpStatus;

public class UpdateClientExistsException extends Exception{
    public UpdateClientExistsException(String msg){
        super(msg);
    }
}
