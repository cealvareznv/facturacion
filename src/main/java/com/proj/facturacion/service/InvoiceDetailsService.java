package com.proj.facturacion.service;

import com.proj.facturacion.model.InvoiceDetails;
import com.proj.facturacion.model.InvoiceDetailsDAO;
import com.proj.facturacion.repository.InvoiceDetailsRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class InvoiceDetailsService {
    @Autowired
    public InvoiceDetailsRepository invoiceDetailsRepository;

    public InvoiceDetails getInvoiceDetailsById(Long id)throws Exception{
        if(id <= 0) {throw new Exception("getInvoiceDetailsById -> El id del detalle de la factura no existe en la BBDD.");}
        Optional<InvoiceDetails> invoiceDetailsOptional = this.invoiceDetailsRepository.findById(id);
        if(invoiceDetailsOptional.isEmpty()){
            log.info("getInvoiceDetailsById -> El id del detalle de factura no existe en la BBDD.");
            throw new Exception("getInvoiceDetailsById -> El id del detalle de factura no existe en la BBDD.");
        }else{
            log.info("getInvoiceById -> Se obtiene la factura con el id: " + id);
            return (invoiceDetailsOptional.get());
        }
    }
    public List<InvoiceDetails> getAllInvoicesDetails(){
        log.info("getAllInvoicesDetails -> Se obtienen todos los detalles de las facturas.");
        return(this.invoiceDetailsRepository.findAll());
    }
    public InvoiceDetails saveNewInvoiceDetails(@NonNull InvoiceDetailsDAO newInvoiceDetails) {
        InvoiceDetails objInvoiceDetails = new InvoiceDetails();
        objInvoiceDetails.setAmoun(newInvoiceDetails.getAmoun());
        objInvoiceDetails.setPrice(newInvoiceDetails.getPrice());
        objInvoiceDetails.setProduct(newInvoiceDetails.getProduct());
        objInvoiceDetails.setInvoice(newInvoiceDetails.getInvoice());
        log.info("saveNewInvoiceDetails -> Detalle de la factura agregado a la BBDD.");
        return (this.invoiceDetailsRepository.saveAndFlush(objInvoiceDetails));
    }

}
