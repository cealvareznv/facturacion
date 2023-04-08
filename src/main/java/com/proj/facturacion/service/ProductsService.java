package com.proj.facturacion.service;

import com.proj.facturacion.model.Products;
import com.proj.facturacion.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductsService {
    final int lenghtCode = 4;
    @Autowired
    public ProductsRepository productsRepository;

    public Products getProductsById(Long id)throws Exception{
        if(id <= 0) {throw new Exception("getProductsById -> El id del producto no existe en la BBDD.");}
        Optional<Products> productsOptional = this.productsRepository.findById(id);
        if(productsOptional.isEmpty()){
            log.info("getProductsById -> El id del producto no existe en la BBDD.");
            throw new Exception("getProductsById -> El id del producto no existe en la BBDD.");
        }else{
            log.info("getProductsById -> Se obtiene el producto con el id: " + id);
            return (productsOptional.get());
        }
    }

    public List<Products> getAllProducts(){
        log.info("getAllProducts -> Se obtienen todos los productos.");
        return(this.productsRepository.findAll());
    }

    public Products saveNewProducts(Products newProduct) throws Exception {
        Optional<Products> productsOptional = this.productsRepository.findByCode(newProduct.getCode());
        if (productsOptional.isPresent()) {
            log.info("saveNewProducts -> El codigo " + newProduct.getCode() + " ya existe en la BBDD y pertene al producto: "  +
                    newProduct.getDescription());
            throw new Exception("saveNewProducts -> El codigo " + newProduct.getCode() +" ya existe en la BBDD.");
        } else {
            if(newProduct.getCode().length() < lenghtCode) {
                log.info("saveNewProducts -> El codigo del producto " + newProduct + " no es valido.");
                throw new Exception("saveNewProducts -> El codigo del producto no es valido.");
            } else {
                log.info("saveNewProducts -> Producto: " + newProduct.getDescription() + " se ingresa a la BBDD.");
                return (this.productsRepository.saveAndFlush(newProduct));
            }
        }
    }

    public Products upgradeProductsById(Products newProduct, Long id) throws Exception {
        if(id <= 0){ throw new Exception("upgradeProductsById -> El id del producto no existe en la BBDD."); }
        if(Objects.isNull(newProduct.getCode()) || "".equalsIgnoreCase(newProduct.getCode())){
            throw new Exception("upgradeProductsById -> El codigo del producto no es valido.");
        }
        if(Objects.isNull(newProduct.getDescription()) || "".equalsIgnoreCase(newProduct.getDescription())){
            throw new Exception("upgradeProductsById -> La descripcion del producto no es valida.");
        }
        if(Objects.isNull(newProduct.getStock()) || (newProduct.getStock() <= 0)){
            throw new Exception("upgradeProductsById -> El producto no tiene stock.");
        }
        if(Objects.isNull(newProduct.getPrice()) || (newProduct.getPrice() <= 0)){
            throw new Exception("upgradeProductsById -> El precio del producto no es valido.");
        }
        Optional<Products> productsOptional = this.productsRepository.findByIdAndCode(id,newProduct.getCode());
        if(productsOptional.isEmpty()) {
            log.info("upgradeProductsById -> El producto no existe en la BBDD.");
            throw new Exception("upgradeProductsById -> El producto no existe en la BBDD.");
        }else{
            log.info("upgradeProductsById -> Se actualizan los datos del producto con Id: " + id +
                    " y code: " + newProduct.getCode());
            Products existProduct = productsOptional.get();
            existProduct.setCode(newProduct.getCode());
            existProduct.setDescription(newProduct.getDescription());
            existProduct.setStock(newProduct.getStock());
            existProduct.setPrice(newProduct.getPrice());
            return(this.productsRepository.saveAndFlush(existProduct));
        }
    }

    public Boolean deleteProductsById(Long id) throws Exception {
        Boolean deleteProducts = false;
        if(id <= 0) { throw new Exception("upgradeProductsById -> El id del producto no existe en la BBDD."); }
        Optional<Products> productsOptional = this.productsRepository.findById(id);
        if(productsOptional.isEmpty()) {
            log.info("upgradeProductsById -> El id" + id + " del producto no existe en la BBDD.");
        }else {
            Products existProduct = productsOptional.get();
            log.info("upgradeProductsById -> El producto con Id: " + existProduct.getId() + " descripcion: " +
                    existProduct.getDescription() + " codigo: " + existProduct.getCode() + " fue borrado de la BBDD.");
            this.productsRepository.delete(existProduct);
            deleteProducts = true;
        }
        return (deleteProducts);
    }
}
