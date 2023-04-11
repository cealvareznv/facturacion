package com.proj.facturacion.service;

import com.proj.facturacion.model.Invoice;
import com.proj.facturacion.model.InvoiceDAO;
import com.proj.facturacion.repository.InvoiceRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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

    public List<Invoice> getAllInvoices(){
        log.info("getAllInvoices -> Se obtienen todas las facturas.");
        return(this.invoiceRepository.findAll());
    }

    public Invoice saveNewInvoice(@NonNull InvoiceDAO newInvoice) {
        Invoice objInvoice = new Invoice();
        objInvoice.setClient(newInvoice.getClient());
        objInvoice.setFec_created(getDateNow());
        objInvoice.setTotal(newInvoice.getTotal());
        log.info("saveNewInvoice -> Nueva Factura ingresada a la BBDD.");
        return (this.invoiceRepository.saveAndFlush(objInvoice));
    }

    private static String getDateNow(){
        LocalDateTime now = LocalDateTime.now();
        return (now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public Invoice upgradeInvoiceById(@NonNull InvoiceDAO newInvoice, Long id) throws Exception {
        if(id <= 0){ throw new Exception("upgradeInvoiceById -> El id de la factura no existe en la BBDD."); }
        if(Objects.isNull(newInvoice.getFec_created()) || "".equalsIgnoreCase(newInvoice.getFec_created())){
            throw new Exception("upgradeInvoiceById -> La fecha de la factura no es valida.");
        }
        if(Objects.isNull(newInvoice.getClient())) {
            throw new Exception("upgradeInvoiceById -> La factura no tiene un cliente valido.");
        }
        Optional<Invoice> invoiceOptional = this.invoiceRepository.findById(id);
        if(invoiceOptional.isEmpty()) {
            log.info("upgradeInvoiceById -> La factura no existe en la BBDD.");
            throw new Exception("upgradeInvoiceById -> La factura no existe en la BBDD.");
        }else{
            Invoice objInvoice = new Invoice();
            objInvoice.setClient(newInvoice.getClient());
            objInvoice.setFec_created(newInvoice.getFec_created());
            objInvoice.setTotal(newInvoice.getTotal());
            log.info("upgradeInvoiceById -> Se actualizan los datos de la factura con Id: " + id);
            return(this.invoiceRepository.saveAndFlush(objInvoice));
        }
    }

}
