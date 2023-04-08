package com.proj.facturacion.controller;

import com.proj.facturacion.model.Products;
import com.proj.facturacion.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductsController {
    @Autowired
    public ProductsService productsService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Products> findById(@PathVariable Long id) throws Exception{
        return (new ResponseEntity<>(this.productsService.getProductsById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Products>> findAll() {
        return (new ResponseEntity<>(this.productsService.getAllProducts(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Products> saveNewProducts(@RequestBody Products product) throws Exception {
        return(new ResponseEntity<>(this.productsService.saveNewProducts(product), HttpStatus.OK));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Products> updateProductsById(@RequestBody Products product, @PathVariable Long id) throws Exception {
        return (new ResponseEntity<>(this.productsService.upgradeProductsById(product,id), HttpStatus.OK));
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteProductsById(@PathVariable Long id) throws Exception {
        return (this.productsService.deleteProductsById(id));
    }
}
