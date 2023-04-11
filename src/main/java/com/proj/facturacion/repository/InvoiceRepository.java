package com.proj.facturacion.repository;

import com.proj.facturacion.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
