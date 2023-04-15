package com.proj.facturacion.service;

import com.proj.facturacion.exception.UpdateClientExistsException;
import com.proj.facturacion.model.Client;
import com.proj.facturacion.repository.ClientRepository;
import com.proj.facturacion.validator.ClientValidator;
import com.proj.facturacion.validator.GlobalValidator;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ClientService {
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    private EntityManager entityManager;

    public Client getClientById(Long id) throws IllegalArgumentException{
        ClientValidator.validateIdClient(id, GlobalValidator.getMethodName());
        log.info(GlobalValidator.getMethodName() + " -> Obteniendo Cliente con el id: " + id);
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            log.error(GlobalValidator.getMethodName() + " -> El id del cliente no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del cliente no existe en la BBDD.");
        }
        return (clientOptional.get());
    }

    public List<Client> getAllClients(){
        log.info(GlobalValidator.getMethodName() + " -> Se obtienen todos los clientes de la BBDD.");
        return(this.clientRepository.findAll());
    }

    public Client saveNewClient(Client newClient) throws IllegalArgumentException, UpdateClientExistsException {
        ClientValidator.validateClient(newClient, GlobalValidator.getMethodName());
        if(this.clientRepository.existsByDniAndDeleted(newClient.getDni(),true)){
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("deletedClientFilter");
            filter.setParameter("isDeleted", true);
            this.clientRepository.updateClientDeletedStatusForDni(false, newClient.getDni());
            session.disableFilter("deletedClientFilter");
            log.info(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClient.getDni() + " se ingresa a la BBDD.");
            throw new UpdateClientExistsException(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClient.getDni() + " se ingresa a la BBDD.");
        }
        if(this.clientRepository.existsByDni(newClient.getDni())){
            log.info(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClient.getDni() + " ya existe en la BBDD");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClient.getDni() +" ya existe en la BBDD.");
        }
        log.info(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClient.getDni() + " se ingresa a la BBDD.");
        return (clientRepository.saveAndFlush(newClient));
    }

    public Client upgradeClientById(Client newClient, Long id) {
        ClientValidator.validateIdClient(id, GlobalValidator.getMethodName());
        ClientValidator.validateClient(newClient, GlobalValidator.getMethodName());
        Optional<Client> clientOptional = this.clientRepository.findByIdAndDni(id, newClient.getDni());
        if(clientOptional.isEmpty()){
            log.error(GlobalValidator.getMethodName() + " -> El cliente con el id: " + id + " y dni: " + newClient.getDni()+ " no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El cliente con el id: " + id + " y dni: " + newClient.getDni()+ " no existe en la BBDD.");
        }
        Client existClient = clientOptional.get();
        existClient.setNombre(newClient.getNombre());
        existClient.setApellido(newClient.getApellido());
        existClient.setDni(newClient.getDni());
        log.info(GlobalValidator.getMethodName() + " -> Se actualizan los datos del cliente con Id: " + id);
        return (this.clientRepository.saveAndFlush(existClient));
    }

    public void deleteClientById(Long id) throws IllegalArgumentException {
        ClientValidator.validateIdClient(id, GlobalValidator.getMethodName());
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()) {
            log.error(GlobalValidator.getMethodName() + " -> El id: " + id + " del cliente no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id" + id + " del cliente no existe en la BBDD.");
        }
        Client existClient = clientOptional.get();
        log.info(GlobalValidator.getMethodName() + " -> El cliente con id: " + existClient.getId() + " y dni: " + existClient.getDni() +" fue borrado de la BBDD.");
        this.clientRepository.deleteById(id);
    }
}
