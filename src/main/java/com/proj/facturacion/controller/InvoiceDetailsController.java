package com.proj.facturacion.controller;

import com.proj.facturacion.model.Invoice;
import com.proj.facturacion.model.InvoiceDAO;
import com.proj.facturacion.model.InvoiceDetails;
import com.proj.facturacion.model.InvoiceDetailsDAO;
import com.proj.facturacion.service.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/invoicedetails")
public class InvoiceDetailsController {
    @Autowired
    public InvoiceDetailsService invoiceDetailsService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDetails> findById(@PathVariable Long id) throws Exception{
        return (new ResponseEntity<>(this.invoiceDetailsService.getInvoiceDetailsById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<InvoiceDetails>> findAll() {
        return (new ResponseEntity<>(this.invoiceDetailsService.getAllInvoicesDetails(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<InvoiceDetails> saveNewInvoice(@RequestBody InvoiceDetailsDAO invoiceDetails) {
        return(new ResponseEntity<>(this.invoiceDetailsService.saveNewInvoiceDetails(invoiceDetails), HttpStatus.OK));
    }
}
