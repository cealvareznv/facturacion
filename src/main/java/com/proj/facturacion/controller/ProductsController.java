package com.proj.facturacion.controller;

import com.proj.facturacion.model.Product;
import com.proj.facturacion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductsController {
    @Autowired
    public ProductService productService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) throws Exception{
        return (new ResponseEntity<>(this.productService.getProductById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Product>> findAll() {
        return (new ResponseEntity<>(this.productService.getAllProducts(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Product> saveNewProducts(@RequestBody Product product) throws Exception {
        return(new ResponseEntity<>(this.productService.saveNewProduct(product), HttpStatus.OK));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProductsById(@RequestBody Product product, @PathVariable Long id) throws Exception {
        return (new ResponseEntity<>(this.productService.upgradeProductById(product,id), HttpStatus.OK));
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteProductById(@PathVariable Long id) throws Exception {
        return (this.productService.deleteProductById(id));
    }
}
