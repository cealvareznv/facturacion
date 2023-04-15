package com.proj.facturacion.validator;

import org.springframework.stereotype.Component;
import java.lang.Thread;

@Component
public class GlobalValidator {
    public static String getMethodName(){
        if(Thread.currentThread().getStackTrace().length > 2){
            return (Thread.currentThread().getStackTrace()[2].getMethodName());
        }else{
            return ("undefined");
        }
    }

    public static Boolean isNumeric(String cadnumber){
        try{
            Integer.parseInt(cadnumber);
            return (Boolean.TRUE);
        }catch (NumberFormatException nfe){
            return (Boolean.FALSE);
        }
    }
}
