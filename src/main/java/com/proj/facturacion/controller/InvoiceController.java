package com.proj.facturacion.controller;

import com.proj.facturacion.model.Invoice;
import com.proj.facturacion.model.InvoiceDTO;
import com.proj.facturacion.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/invoice")
public class InvoiceController {
    @Autowired
    public InvoiceService invoiceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDTO> findById(@PathVariable Long id) throws Exception{
        return (new ResponseEntity<>(this.invoiceService.getInvoiceById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<InvoiceDTO>> findAll() {
        return (new ResponseEntity<>(this.invoiceService.getAllInvoices(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Invoice> saveNewInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return(new ResponseEntity<>(this.invoiceService.saveNewInvoice(invoiceDTO), HttpStatus.OK));
    }
}

