package com.proj.facturacion.service;

import com.proj.facturacion.model.Product;
import com.proj.facturacion.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {
    final int lenghtCode = 4;
    @Autowired
    public ProductRepository productRepository;

    public Product getProductById(Long id)throws Exception{
        if(id <= 0) {throw new Exception("getProductById -> El id del producto no existe en la BBDD.");}
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(productOptional.isEmpty()){
            log.info("getProductById -> El id del producto no existe en la BBDD.");
            throw new Exception("getProductById -> El id del producto no existe en la BBDD.");
        }else{
            log.info("getProductById -> Se obtiene el producto con el id: " + id);
            return (productOptional.get());
        }
    }

    public List<Product> getAllProducts(){
        log.info("getAllProducts -> Se obtienen todos los productos.");
        return(this.productRepository.findAll());
    }

    public Product saveNewProduct(Product newProduct) throws Exception {
        Optional<Product> productOptional = this.productRepository.findByCode(newProduct.getCode());
        if (productOptional.isPresent()) {
            log.info("saveNewProduct -> El codigo " + newProduct.getCode() + " ya existe en la BBDD y pertene al producto: "  +
                    newProduct.getDescription());
            throw new Exception("saveNewProduct -> El codigo " + newProduct.getCode() +" ya existe en la BBDD.");
        } else {
            if(newProduct.getCode().length() < lenghtCode) {
                log.info("saveNewProduct -> El codigo del producto " + newProduct + " no es valido.");
                throw new Exception("saveNewProduct -> El codigo del producto no es valido.");
            } else {
                log.info("saveNewProduct -> Producto: " + newProduct.getDescription() + " se ingresa a la BBDD.");
                return (this.productRepository.saveAndFlush(newProduct));
            }
        }
    }

    public Product upgradeProductById(Product newProduct, Long id) throws Exception {
        if(id <= 0){ throw new Exception("upgradeProductById -> El id del producto no existe en la BBDD."); }
        if(Objects.isNull(newProduct.getCode()) || "".equalsIgnoreCase(newProduct.getCode())){
            throw new Exception("upgradeProductById -> El codigo del producto no es valido.");
        }
        if(Objects.isNull(newProduct.getDescription()) || "".equalsIgnoreCase(newProduct.getDescription())){
            throw new Exception("upgradeProductById -> La descripcion del producto no es valida.");
        }
        if(Objects.isNull(newProduct.getStock()) || (newProduct.getStock() <= 0)){
            throw new Exception("upgradeProductById -> El producto no tiene stock.");
        }
        if(Objects.isNull(newProduct.getPrice()) || (newProduct.getPrice() <= 0)){
            throw new Exception("upgradeProductById -> El precio del producto no es valido.");
        }
        Optional<Product> productOptional = this.productRepository.findByIdAndCode(id,newProduct.getCode());
        if(productOptional.isEmpty()) {
            log.info("upgradeProductById -> El producto no existe en la BBDD.");
            throw new Exception("upgradeProductById -> El producto no existe en la BBDD.");
        }else{
            log.info("upgradeProductById -> Se actualizan los datos del producto con Id: " + id +
                    " y code: " + newProduct.getCode());
            Product existProduct = productOptional.get();
            existProduct.setCode(newProduct.getCode());
            existProduct.setDescription(newProduct.getDescription());
            existProduct.setStock(newProduct.getStock());
            existProduct.setPrice(newProduct.getPrice());
            return(this.productRepository.saveAndFlush(existProduct));
        }
    }

    public Boolean deleteProductById(Long id) throws Exception {
        Boolean deleteProduct = false;
        if(id <= 0) { throw new Exception("upgradeProductById -> El id del producto no existe en la BBDD."); }
        Optional<Product> productsOptional = this.productRepository.findById(id);
        if(productsOptional.isEmpty()) {
            log.info("upgradeProductById -> El id" + id + " del producto no existe en la BBDD.");
        }else {
            Product existProduct = productsOptional.get();
            log.info("upgradeProductById -> El producto con Id: " + existProduct.getId() + " descripcion: " +
                    existProduct.getDescription() + " codigo: " + existProduct.getCode() + " fue borrado de la BBDD.");
            this.productRepository.delete(existProduct);
            deleteProduct = true;
        }
        return (deleteProduct);
    }
}
