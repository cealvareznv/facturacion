package com.proj.facturacion.controller;

import com.proj.facturacion.model.Product;
import com.proj.facturacion.model.ProductDTO;
import com.proj.facturacion.service.ProductService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return (new ResponseEntity<>(this.productService.getProductById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Product>> findAll() {
        return (new ResponseEntity<>(this.productService.getAllProducts(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Product> saveNewProducts(@Valid @RequestBody Product product) {
        return(new ResponseEntity<>(this.productService.saveNewProduct(product), HttpStatus.OK));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProductsById(@RequestBody Product product, @PathVariable Long id) {
        return (new ResponseEntity<>(this.productService.upgradeProductById(product,id), HttpStatus.OK));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProductById(@PathVariable Long id) {
        this.productService.deleteProductById(id);
    }
}
