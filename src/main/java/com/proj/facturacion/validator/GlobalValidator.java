package com.proj.facturacion.validator;

import org.springframework.stereotype.Component;
import java.lang.Thread;
import java.util.List;

@Component
public class GlobalValidator {
    public static String getMethodName(){
        if(Thread.currentThread().getStackTrace().length > 2){
            return (Thread.currentThread().getStackTrace()[2].getMethodName());
        }else{
            return ("undefined");
        }
    }
}
