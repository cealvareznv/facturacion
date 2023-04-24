package com.proj.facturacion.service;

import com.proj.facturacion.model.*;
import com.proj.facturacion.repository.ClientRepository;
import com.proj.facturacion.repository.InvoiceRepository;
import com.proj.facturacion.repository.ProductRepository;
import com.proj.facturacion.validator.GlobalValidator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.lang.*;
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
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(invoice.getInvoiceDetails().get(index).getProduct().getId());
            productDTO.setPrice(invoice.getInvoiceDetails().get(index).getProduct().getPrice());
            productDTO.setCode(invoice.getInvoiceDetails().get(index).getProduct().getCode());
            productDTO.setDescription(invoice.getInvoiceDetails().get(index).getProduct().getDescription());
            invoiceDetailsDTO.setProductDTO(productDTO);
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
                for (int indexDetails = 0; indexDetails < invoiceList.get(index).getInvoiceDetails().size(); indexDetails++) {
                    InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
                    invoiceDetailsDTO.setInvoiceDetailId(invoiceList.get(index).getInvoiceDetails().get(indexDetails).getInvoiceDetailId());
                    invoiceDetailsDTO.setPrice(invoiceList.get(index).getInvoiceDetails().get(indexDetails).getPrice());
                    invoiceDetailsDTO.setAmoun(invoiceList.get(index).getInvoiceDetails().get(indexDetails).getAmoun());
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(invoiceList.get(index).getInvoiceDetails().get(indexDetails).getProduct().getId());
                    productDTO.setPrice(invoiceList.get(index).getInvoiceDetails().get(indexDetails).getProduct().getPrice());
                    productDTO.setCode(invoiceList.get(index).getInvoiceDetails().get(indexDetails).getProduct().getCode());
                    productDTO.setDescription(invoiceList.get(index).getInvoiceDetails().get(indexDetails).getProduct().getDescription());
                    invoiceDetailsDTO.setProductDTO(productDTO);
                    invoiceDetailsDTOList.add(indexDetails,invoiceDetailsDTO);
                }
                invoiceDTO.setInvoiceDetailsDTO(invoiceDetailsDTOList);
                invoiceDTOList.add(index,invoiceDTO);
            }
            log.info("getAllInvoices -> Se obtienen todas las facturas.");
        }else{log.info(GlobalValidator.getMethodName() + " -> No existen facturas registradas en BBDD.");}
        return(invoiceDTOList);
    }

    public InvoiceDTO saveNewInvoice(@NonNull InvoiceDTO newInvoiceDTO) {
        //Hacer: Se tiene que validad que el ID del cleinte no sea nulo o no exista por una anotacion
        validateInvoiceDTOClient(newInvoiceDTO);
        //Se valida que la factura tenga detalles de la compra
        if(newInvoiceDTO.getInvoiceDetailsDTO().isEmpty()){
            log.info(GlobalValidator.getMethodName() + " -> Factura no valida, no tiene detalle.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Factura no valida, no tiene detalle.");
        }
        Double calculoTotal = 0.0;
        Invoice invoice = new Invoice();
        List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();
        for(int index=0;index < newInvoiceDTO.getInvoiceDetailsDTO().size();index++){
            //Se calculan los precios del detalle de productos en la factura y se actualiza el DTO
            Long productId = newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProductDTO().getId();
            Integer productAmoun = newInvoiceDTO.getInvoiceDetailsDTO().get(index).getAmoun();
            Double priceProduct = this.productRepository.getPriceById(productId);
            Double calculoPrice = priceProduct * productAmoun;
            newInvoiceDTO.getInvoiceDetailsDTO().get(index).setPrice(calculoPrice);
            calculoTotal += calculoPrice;
            //Se asignan los valores del detalle de la factura desde el DTO a la Entidad InvoiceDetails
            InvoiceDetails invoiceDetails = new InvoiceDetails();
            Product product = new Product();
            product.setId(newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProductDTO().getId());
            product.setCode(newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProductDTO().getCode());
            product.setPrice(newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProductDTO().getPrice());
            product.setDescription(newInvoiceDTO.getInvoiceDetailsDTO().get(index).getProductDTO().getDescription());
            invoiceDetails.setProduct(product);
            invoiceDetails.setPrice(newInvoiceDTO.getInvoiceDetailsDTO().get(index).getPrice());
            invoiceDetails.setAmoun(productAmoun);
            invoiceDetails.setInvoice(invoice);
            //Se verifica el stock y se actualiza la disponibilidad de los productos
            Integer currentStock = this.productRepository.getStockById(productId);
            if(currentStock <= 0 && currentStock < productAmoun){
                log.info(GlobalValidator.getMethodName() + " -> Stock insuficiente. Stock: ");
                throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Stock insuficiente.");
            }
            currentStock -= productAmoun;
            invoiceDetailsList.add(index,invoiceDetails);
            this.productRepository.updateProductStockById(currentStock,productId);
        }
        newInvoiceDTO.setTotal(calculoTotal);
        newInvoiceDTO.setFec_created(getDateNow());
        //Se asignan los valores de la factura desde el DTO a la Entidad Invoice
        invoice.setClient(newInvoiceDTO.getClient());
        invoice.setFec_created(newInvoiceDTO.getFec_created());
        invoice.setTotal(newInvoiceDTO.getTotal());
        invoice.setInvoiceDetails(invoiceDetailsList);
        //Se inserta la factura en la BBDD
        log.info(GlobalValidator.getMethodName() + " -> Factura ingresada a la BBDD.");
        this.invoiceRepository.saveAndFlush(invoice);
        //Se actualiza el DTO con la respuesta de la inserción de la factura en BBDD
        newInvoiceDTO.setId(invoice.getId());
        List<InvoiceDetailsDTO> invoiceDetailsDTOList = new ArrayList<>();
        for(int index=0;index < invoiceDetailsList.size();index++) {
            InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
            invoiceDetailsDTO.setInvoiceDetailId(invoiceDetailsList.get(index).getInvoiceDetailId());
            invoiceDetailsDTO.setAmoun(invoiceDetailsList.get(index).getAmoun());
            invoiceDetailsDTO.setPrice(invoiceDetailsList.get(index).getPrice());
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(invoiceDetailsList.get(index).getProduct().getId());
            productDTO.setPrice(invoiceDetailsList.get(index).getProduct().getPrice());
            productDTO.setCode(invoiceDetailsList.get(index).getProduct().getCode());
            productDTO.setDescription(invoiceDetailsList.get(index).getProduct().getDescription());
            invoiceDetailsDTO.setProductDTO(productDTO);
            invoiceDetailsDTOList.add(index,invoiceDetailsDTO);
        }
        newInvoiceDTO.setInvoiceDetailsDTO(invoiceDetailsDTOList);
        return (newInvoiceDTO);
    }

    private void validateInvoiceDTOClient(InvoiceDTO invoiceDTO){
        Long clientId = invoiceDTO.getClient().getId();
        Optional<Client> clientOptional = this.clientRepository.findById(clientId);
        if(clientOptional.isPresent()){
            Client client = clientOptional.get();
            invoiceDTO.setClient(client);
        }else{
            log.info(GlobalValidator.getMethodName() + " -> Cliente no existe en el BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + "-> Cliente no existe en el BBDD.");
        }
    }
    private String getDateNow(){
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
