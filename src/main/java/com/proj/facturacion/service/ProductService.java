package com.proj.facturacion.service;

import com.proj.facturacion.model.Product;
import com.proj.facturacion.repository.ProductRepository;
import com.proj.facturacion.validator.GlobalValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {
    @Autowired
    public ProductRepository productRepository;

    public Product getProductById(Long id)throws IllegalArgumentException{
        if(id <= 0) {throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del producto no existe en la BBDD.");}
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(productOptional.isEmpty()){
            log.info(GlobalValidator.getMethodName() + " -> El id del producto no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del producto no existe en la BBDD.");
        }
        log.info(GlobalValidator.getMethodName() + " -> Obteniendo producto con el id: " + id);
        return (productOptional.get());
    }

    public List<Product> getAllProducts(){
        log.info(GlobalValidator.getMethodName() + " -> Se obtienen todos los productos de la BBDD.");
        return(this.productRepository.findAll());
    }

    public Product saveNewProduct(Product newProduct) throws IllegalArgumentException {
        Optional<Product> productOptional = this.productRepository.findByCode(newProduct.getCode());
        if (productOptional.isPresent()) {
            log.info(GlobalValidator.getMethodName() + " -> El codigo " + newProduct.getCode() + " ya existe en la BBDD y pertene al producto: "  +
                    newProduct.getDescription());
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El codigo " + newProduct.getCode() +" ya existe en la BBDD.");
        }
        this.productRepository.saveAndFlush(newProduct);
        log.info(GlobalValidator.getMethodName() + " -> Se ingreso el producto " + newProduct.getDescription() + " con id: " + newProduct.getId() + " a la BBDD.");
        return (newProduct);
    }

    public Product upgradeProductById(Product newProduct, Long id) throws IllegalArgumentException {
        if(id <= 0){ throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del producto no existe en la BBDD."); }
        Optional<Product> productOptional = this.productRepository.findByIdAndCode(id, newProduct.getCode());
        if(productOptional.isEmpty()) {
            log.error(GlobalValidator.getMethodName() + " -> El producto no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El producto no existe en la BBDD.");
        }
        Product existProduct = productOptional.get();
        if ((!newProduct.getDescription().isBlank()) && (!existProduct.getDescription().equals(newProduct.getDescription()))) {
            existProduct.setDescription(newProduct.getDescription());
        }
        if((newProduct.getStock() != null) && (newProduct.getStock() > 0) && (!existProduct.getStock().equals(newProduct.getStock()))) {
            existProduct.setStock(newProduct.getStock());
        }
        if((newProduct.getPrice() != null) && (newProduct.getPrice() > 0) && !existProduct.getPrice().equals(newProduct.getPrice())) {
            existProduct.setPrice(newProduct.getPrice());
        }
        log.info(GlobalValidator.getMethodName() + " -> Se actualizo datos del producto " + newProduct.getDescription() + " con id: " + id);
        return(this.productRepository.saveAndFlush(existProduct));
    }

    public void deleteProductById(Long id) throws IllegalArgumentException {
        if(id <= 0) { throw new IllegalArgumentException(GlobalValidator.getMethodName() + " ->   El id del producto no existe en la BBDD."); }
        Optional<Product> productsOptional = this.productRepository.findById(id);
        if(productsOptional.isEmpty()) {
            log.info(GlobalValidator.getMethodName() + " -> El id " + id + " del producto no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id " + id + " del producto no existe en la BBDD.");
        }
        Product existsProduct = productsOptional.get();
        log.info(GlobalValidator.getMethodName() + " -> El producto con Id: " + existsProduct.getId() + " descripcion: " +
                existsProduct.getDescription() + " codigo: " + existsProduct.getCode() + " fue borrado de la BBDD.");
        this.productRepository.delete(existsProduct);
    }
}
