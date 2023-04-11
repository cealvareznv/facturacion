package com.proj.facturacion.repository;

import com.proj.facturacion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByCode(String code);
    Optional<Product> findByIdAndCode(Long id, String code);
}
