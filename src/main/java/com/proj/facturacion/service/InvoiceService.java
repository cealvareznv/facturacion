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
        validateInvoiceDTOClient(newInvoiceDTO);
        validateInvoiceDTOInvoiceDetails(newInvoiceDTO);
        calculatePrice(newInvoiceDTO);
        newInvoiceDTO.setFec_created(getDateNow());
        Invoice invoice = new Invoice();
        //Se asignan los valores de la factura desde el DTO a la Entidad Invoice
        invoice.setClient(newInvoiceDTO.getClient());
        invoice.setFec_created(newInvoiceDTO.getFec_created());
        invoice.setTotal(newInvoiceDTO.getTotal());
        List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();
        copyFromDtoInvoiceDetailsToEntityInvoiceDetails(invoice,newInvoiceDTO.getInvoiceDetailsDTO(),invoiceDetailsList);
        invoice.setInvoiceDetails(invoiceDetailsList);
        //Se inserta la factura en la BBDD
        log.info(GlobalValidator.getMethodName() + " -> Factura ingresada a la BBDD.");
        this.invoiceRepository.saveAndFlush(invoice);
        //Se actualiza el DTO con la respuesta de la inserción de la factura en BBDD
        newInvoiceDTO.setId(invoice.getId());
        return (newInvoiceDTO);
    }

    private void validateInvoiceDTOClient(InvoiceDTO invoiceDTO){
        Long clientId = invoiceDTO.getClient().getId();
        if (clientId == null) {
            log.info(GlobalValidator.getMethodName() + " -> Id de cliente no valido. La factura no esta asociada a un cliente.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Id de cliente no valido. La factura no esta asociada a un cliente.");
        }
        if (clientId > 0) {
            Optional<Client> clientOptional = this.clientRepository.findById(clientId);
            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                invoiceDTO.setClient(client);
            }else{
                log.info(GlobalValidator.getMethodName() + " -> Cliente no existe en el BBDD.");
                throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Cliente no existe en el BBDD.");
            }
        }
    }

    private void validateInvoiceDTOInvoiceDetails(InvoiceDTO invoiceDTO){
        List<InvoiceDetailsDTO> invoiceDetailsDTOList = invoiceDTO.getInvoiceDetailsDTO().stream().toList();
        if(invoiceDetailsDTOList.isEmpty()){
            log.info(GlobalValidator.getMethodName() + " -> Factura no valida, no tiene detalle.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Factura no valida, no tiene detalle.");
        }
        for(InvoiceDetailsDTO idDtoList: invoiceDetailsDTOList){
            if (idDtoList.getAmoun() <= 0){
                log.info(GlobalValidator.getMethodName() + " -> La cantidad del producto debe ser mayor que cero.");
                throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> La cantidad del producto debe ser mayor que cero.");
            }
            if (idDtoList.getProductDTO().getId() <= 0){
                log.info(GlobalValidator.getMethodName() + " -> Id del producto no valido. Producto no existente.");
                throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Id del producto no valido. Producto no existente.");
            }
            Long productId = idDtoList.getProductDTO().getId();
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()){
                Product product = productOptional.get();
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setCode(product.getCode());
                productDTO.setDescription(product.getDescription());
                productDTO.setPrice(product.getPrice());
                productDTO.setStock(product.getStock());
                idDtoList.setProductDTO(productDTO);
            }
        }
    }

    private void calculatePrice(InvoiceDTO invoiceDTO){
        List<InvoiceDetailsDTO> invoiceDetailsDTOList = invoiceDTO.getInvoiceDetailsDTO().stream().toList();
        Double calculoTotal = 0.0;
        for(InvoiceDetailsDTO idDtoList: invoiceDetailsDTOList){
            Double priceProduct = idDtoList.getProductDTO().getPrice();
            Integer productAmoun = idDtoList.getAmoun();
            //Se verifica la disponibilidad del producto en el stock
            Integer currentStock = idDtoList.getProductDTO().getStock();
            if((currentStock <= 0) && (currentStock < productAmoun)){
                log.info(GlobalValidator.getMethodName() + " -> Stock insuficiente.");
                throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> Stock insuficiente.");
            }
            //Se actualiza el stock del producto
            currentStock -= productAmoun;
            ProductDTO productDTO = idDtoList.getProductDTO();
            productDTO.setStock(currentStock);
            //Se actualiza el stock en BBDD del producto
            this.productRepository.updateProductStockById(productDTO.getStock(), productDTO.getId());
            //Se asigna el producto con sus atributos al objeto InvoiceDetails List
            idDtoList.setProductDTO(productDTO);
            //Se asigna el precio del producto al momento de la compra
            idDtoList.setPrice(priceProduct);
            //Se calcula el precio del producto por la cantidad solicitada, para realizar el calculo total de la factura
            calculoTotal += (priceProduct * productAmoun);
        }
        invoiceDTO.setTotal(calculoTotal);
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

    private void copyFromDtoInvoiceDetailsToEntityInvoiceDetails(Invoice invoice, List<InvoiceDetailsDTO> fromDtoList,List<InvoiceDetails> toEntityList){
        for(InvoiceDetailsDTO invoiceDetailsDTO: fromDtoList){
            InvoiceDetails invoiceDetails = new InvoiceDetails();
            Product product = new Product();
            product.setId(invoiceDetailsDTO.getProductDTO().getId());
            product.setCode(invoiceDetailsDTO.getProductDTO().getCode());
            product.setPrice(invoiceDetailsDTO.getProductDTO().getPrice());
            product.setStock(invoiceDetailsDTO.getProductDTO().getStock());
            product.setDescription(invoiceDetailsDTO.getProductDTO().getDescription());
            invoiceDetails.setPrice(invoiceDetailsDTO.getPrice());
            invoiceDetails.setAmoun(invoiceDetailsDTO.getAmoun());
            invoiceDetails.setProduct(product);
            invoiceDetails.setInvoice(invoice);
            toEntityList.add(invoiceDetails);
        }
    }
}
