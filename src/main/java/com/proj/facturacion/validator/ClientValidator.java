package com.proj.facturacion.validator;

import com.proj.facturacion.model.Client;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

@Component
public class ClientValidator {
    public static void validateIdClient(Long id, String metodo) throws IllegalArgumentException {
        if(id <= 0){throw new IllegalArgumentException(metodo + " -> El id del cliente no es valido.");}
    }
    public static void validateClient(Client client, String metodo) throws IllegalArgumentException {
        if(client == null){throw new IllegalArgumentException(metodo + " -> El cliente esta vacio o es nulo.");}
        validateContainsStringData("nombre", client.getNombre(),metodo);
        validateContainsStringData("apellido", client.getApellido(),metodo);
        validateContainsStringData("dni", client.getDni(),metodo);
        String newDocNumber = FormatterDni(client.getDni());
        client.setDni(newDocNumber);
    }

    private static void validateContainsStringData(String attribute, String data, String metodo) throws IllegalArgumentException {
        if(data.isBlank()){throw new IllegalArgumentException(metodo + " -> El " + attribute + " del cliente no es valido.");}
        boolean validateData = switch (attribute) {
            case "nombre", "apellido" -> data.matches("^[A-Za-z]+\\s?[A-Za-z]+$");
            case "dni" -> data.matches("^([A-Z]){0,2}(\\d{1,3}\\.?\\d{3}\\.?\\d{3})([A-Z]){0,2}$");
            default -> false;
        };
        if (!validateData){throw new IllegalArgumentException(metodo + " -> El " + attribute + " no es valido.");}
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
