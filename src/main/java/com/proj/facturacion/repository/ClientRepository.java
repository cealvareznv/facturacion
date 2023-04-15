package com.proj.facturacion.repository;

import com.proj.facturacion.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByIdAndDni(Long id, String docnumber);
    boolean existsByDni(String docnumber);
    boolean existsByDniAndDeleted(String docnumber, boolean deleted);
    @Modifying
    @Transactional
    @Query(value = "UPDATE clients SET deleted = ? WHERE docnumber = ?", nativeQuery = true)
    void updateClientDeletedStatusForDni(boolean status, String dni);
}
