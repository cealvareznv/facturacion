package com.proj.facturacion.repository;

import com.proj.facturacion.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {
    Optional<Products> findByCode(String code);
    Optional<Products> findByIdAndCode(Long id, String code);
}
