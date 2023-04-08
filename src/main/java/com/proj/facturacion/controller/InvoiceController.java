package com.proj.facturacion.controller;


import com.proj.facturacion.model.Invoice;
import com.proj.facturacion.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/invoice")
public class InvoiceController {
    @Autowired
    public InvoiceService invoiceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable Long id) throws Exception{
        return (new ResponseEntity<>(this.invoiceService.getInvoiceById(id), HttpStatus.OK));
    }
}
