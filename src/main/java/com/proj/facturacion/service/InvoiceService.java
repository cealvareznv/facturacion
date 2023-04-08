package com.proj.facturacion.service;

import com.proj.facturacion.model.Invoice;
import com.proj.facturacion.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceService {
    @Autowired
    public InvoiceRepository invoiceRepository;

    public Invoice getInvoiceById(Long id)throws Exception{
        if(id <= 0) {throw new Exception("getInvoiceById -> El id de la factura no existe en la BBDD.");}
        Optional<Invoice> invoiceOptional = this.invoiceRepository.findById(id);
        if(invoiceOptional.isEmpty()){
            log.info("getInvoiceById -> El id de la factura no existe en la BBDD.");
            throw new Exception("getInvoiceById -> El id de la factura no existe en la BBDD.");
        }else{
            log.info("getInvoiceById -> Se obtiene la factura con el id: " + id);
            return (invoiceOptional.get());
        }
    }

}
