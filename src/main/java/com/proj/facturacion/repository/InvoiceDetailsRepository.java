package com.proj.facturacion.repository;

import com.proj.facturacion.model.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails,Long> {
}
