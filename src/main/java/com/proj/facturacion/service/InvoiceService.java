package com.proj.facturacion.service;

import com.proj.facturacion.model.Invoice;
import com.proj.facturacion.model.InvoiceDTO;
import com.proj.facturacion.model.InvoiceDetails;
import com.proj.facturacion.model.InvoiceDetailsDTO;
import com.proj.facturacion.repository.ClientRepository;
import com.proj.facturacion.repository.InvoiceRepository;
import com.proj.facturacion.repository.ProductRepository;
import com.proj.facturacion.validator.GlobalValidator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class InvoiceService {
    @Autowired
    public InvoiceRepository invoiceRepository;
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public ProductRepository productRepository;

    public InvoiceDTO getInvoiceById(Long id)throws IllegalArgumentException{
        if(id <= 0){throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id de la factura no existe en la BBDD.");}
        Optional<Invoice> invoiceOptional = this.invoiceRepository.findById(id);
        if(invoiceOptional.isEmpty()){
            log.info(GlobalValidator.getMethodName() + " -> El id de la factura no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id de la factura no existe en la BBDD.");
        }
        Invoice invoice = invoiceOptional.get();
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setClient(invoice.getClient());
        invoiceDTO.setFec_created(invoice.getFec_created());
        invoiceDTO.setTotal(invoice.getTotal());
        List<InvoiceDetailsDTO> invoiceDetailsDTOList = new ArrayList<>();
        for (int index = 0; index < invoice.getInvoiceDetails().size(); index++) {
            InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
            invoiceDetailsDTO.setInvoiceDetailId(invoice.getInvoiceDetails().get(index).getInvoiceDetailId());
            invoiceDetailsDTO.setPrice(invoice.getInvoiceDetails().get(index).getPrice());
            invoiceDetailsDTO.setAmoun(invoice.getInvoiceDetails().get(index).getAmoun());
            invoiceDetailsDTO.setProduct(invoice.getInvoiceDetails().get(index).getProduct());
            invoiceDetailsDTOList.add(index,invoiceDetailsDTO);
        }
        invoiceDTO.setInvoiceDetailsDTO(invoiceDetailsDTOList);
        log.info("getInvoiceById -> Se obtiene la factura con el id: " + id);
        return (invoiceDTO);
    }

    public List<InvoiceDTO> getAllInvoices(){
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        List<Invoice> invoiceList = this.invoiceRepository.findAll();
        if (invoiceList != null) {
            for(int index=0; index < invoiceList.size(); index++){
                InvoiceDTO invoiceDTO = new InvoiceDTO();
                invoiceDTO.setId(invoiceList.get(index).getId());
                invoiceDTO.setClient(invoiceList.get(index).getClient());
                invoiceDTO.setFec_created(invoiceList.get(index).getFec_created());
                invoiceDTO.setTotal(invoiceList.get(index).getTotal());
                List<InvoiceDetailsDTO> invoiceDetailsDTOList = new ArrayList<>();
                for (int indexD = 0; indexD < invoiceList.get(index).getInvoiceDetails().size(); indexD++) {
                    InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
                    invoiceDetailsDTO.setInvoiceDetailId(invoiceList.get(index).getInvoiceDetails().get(indexD).getInvoiceDetailId());
                    invoiceDetailsDTO.setPrice(invoiceList.get(index).getInvoiceDetails().get(indexD).getPrice());
                    invoiceDetailsDTO.setAmoun(invoiceList.get(index).getInvoiceDetails().get(indexD).getAmoun());
                    invoiceDetailsDTO.setProduct(invoiceList.get(index).getInvoiceDetails().get(indexD).getProduct());
                    invoiceDetailsDTOList.add(indexD,invoiceDetailsDTO);
                }
                invoiceDTO.setInvoiceDetailsDTO(invoiceDetailsDTOList);
                invoiceDTOList.add(index,invoiceDTO);
            }
            log.info("getAllInvoices -> Se obtienen todas las facturas.");
        }else{log.info(GlobalValidator.getMethodName() + " -> No existen facturas registradas en BBDD.");}
        return(invoiceDTOList);
    }

    public Invoice saveNewInvoice(@NonNull InvoiceDTO newInvoiceDTO) {
        if (!clientRepository.existsByDni(newInvoiceDTO.getClient().getDni())) {
            log.info(GlobalValidator.getMethodName() + " -> Factura no valida, cliente no existe en el BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + "-> Factura no valida, cliente no existe en el BBDD.");
        }
        if(newInvoiceDTO.getInvoiceDetailsDTO().isEmpty()){
            log.info(GlobalValidator.getMethodName() + " -> Factura no valida, no tiene detalle.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Factura no valida, no tiene detalle.");
        }
        newInvoiceDTO.setFec_created(getDateNow());
        Double calculoTotal = 0.0;
        for(int index=0; index < newInvoiceDTO.getInvoiceDetailsDTO().size();index++){
            Long productId = newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProduct().getId();
            Double priceProduct = this.productRepository.getPriceById(productId);
            Double calculoPrice = priceProduct * newInvoiceDTO.getInvoiceDetailsDTO().get(index).getAmoun();
            newInvoiceDTO.getInvoiceDetailsDTO().get(index).setPrice(calculoPrice);
            calculoTotal += calculoPrice;
        }
        newInvoiceDTO.setTotal(calculoTotal);
        Invoice invoice = new Invoice();
        invoice.setClient(newInvoiceDTO.getClient());
        invoice.setFec_created(newInvoiceDTO.getFec_created());
        invoice.setTotal(newInvoiceDTO.getTotal());
        List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();
        for(int index=0; index < newInvoiceDTO.getInvoiceDetailsDTO().size();index++) {
            InvoiceDetails invoiceDetails = new InvoiceDetails();
            Long productId = newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProduct().getId();
            Integer productAmoun = newInvoiceDTO.getInvoiceDetailsDTO().get(index).getAmoun();
            invoiceDetails.setProduct(newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProduct());
            invoiceDetails.setPrice(newInvoiceDTO.getInvoiceDetailsDTO().get(index).getPrice());
            invoiceDetails.setAmoun(productAmoun);
            Integer currentStock = this.productRepository.getStockById(productId);
            currentStock -= productAmoun;
            invoiceDetailsList.add(index,invoiceDetails);
            this.productRepository.updateProductStockById(currentStock,productId);
        }
        invoice.setInvoiceDetails(invoiceDetailsList);
        log.info(GlobalValidator.getMethodName() + " -> Factura ingresada a la BBDD.");
        return (this.invoiceRepository.saveAndFlush(invoice));
    }

    public String getDateNow(){
        RestTemplate restTemplate = new RestTemplate();
        String urlUTC = "http://worldclockapi.com/api/json/utc/now";
        Map<String, String> response;
        try {
            response = restTemplate.getForObject(urlUTC, Map.class);
            if (response == null) {throw new NullPointerException(GlobalValidator.getMethodName() +" -> Servicio REST vacio.");}
            String currentDateTime = response.get("currentDateTime");
            //Se convierte la fecha de String a LocalDateTime
            DateTimeFormatter currentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
            LocalDateTime currentLocalDateTime = LocalDateTime.parse(currentDateTime,currentDateTimeFormatter);
            //Se convierte la fecha de formato LocalDateTime a Date
            Date currentDate = Date.from(currentLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
            //Se obtine el Time Zone con el Etc/GMT+6 <Etc/GMT+3>
            TimeZone timeZone = TimeZone.getTimeZone("Etc/GMT+6");
            SimpleDateFormat currentSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentSimpleDateFormat.setTimeZone(timeZone);
            return (currentSimpleDateFormat.format(currentDate));
        }catch (Exception exp){
            LocalDateTime now = LocalDateTime.now();
            return (now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }
}
