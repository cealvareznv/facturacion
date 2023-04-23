package com.proj.facturacion.repository;

import com.proj.facturacion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByCode(String code);
    Optional<Product> findByIdAndCode(Long id, String code);
    @Query(value = "SELECT price FROM products WHERE id = ?", nativeQuery = true)
    Double getPriceById(Long id);
    @Query(value = "SELECT stock FROM products WHERE id = ?", nativeQuery = true)
    Integer getStockById(Long id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET stock = ? WHERE id = ?", nativeQuery = true)
    void updateProductStockById(Integer stock, Long id);
}
