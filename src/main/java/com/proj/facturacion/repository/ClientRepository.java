package com.proj.facturacion.repository;

import com.proj.facturacion.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByIdAndDni(Long id, String docnumber);
    Optional<Client> findByDni(String docnumber);
    boolean existsByDni(String docnumber);
    boolean existsByDniAndDeletedTrue(String docnumber);
    List<Client> findByDeletedFalse();
    @Query(value = "SELECT MAX(c.id) FROM clients c", nativeQuery = true)
    Long getLastById();
    Long getIdByDni(String docnumber);
    @Modifying
    @Transactional
    @Query(value = "UPDATE clients SET deleted = ? WHERE docnumber = ?", nativeQuery = true)
    void updateClientDeletedStatusForDni(boolean status, String docnumber);
}
