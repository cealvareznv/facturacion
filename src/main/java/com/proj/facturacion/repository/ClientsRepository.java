package com.proj.facturacion.repository;

import com.proj.facturacion.model.Clients;
import com.proj.facturacion.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Clients,Long> {
    Optional<Clients> findByDni(String docnumber);
}
