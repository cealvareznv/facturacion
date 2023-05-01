package com.proj.facturacion.service;

import com.proj.facturacion.model.Client;
import com.proj.facturacion.model.ClientDTO;
import com.proj.facturacion.repository.ClientRepository;
import com.proj.facturacion.validator.GlobalValidator;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ClientService {
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    private EntityManager entityManager;

    public ClientDTO getClientById(Long id) throws IllegalArgumentException{
        if(id <= 0){throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del cliente no es valido.");}
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            log.error(GlobalValidator.getMethodName() + " -> El id del cliente no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del cliente no existe en la BBDD.");
        }
        ClientDTO clientDTO = new ClientDTO();
        Client client = clientOptional.get();
        clientDTO.setId(client.getId());
        clientDTO.setNombre(client.getNombre());
        clientDTO.setApellido(client.getApellido());
        clientDTO.setDni(client.getDni());
        log.info(GlobalValidator.getMethodName() + " -> Obteniendo Cliente con el id: " + id);
        return (clientDTO);
    }

    public List<ClientDTO> getAllClients(){
        List<ClientDTO> clientDTOList = new ArrayList<>();
        List<Client> clientList = this.clientRepository.findByDeletedFalse();
        if(clientList.isEmpty()){return (clientDTOList);}
        for(int index=0; index < clientList.size(); index++){
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(clientList.get(index).getId());
            clientDTO.setNombre(clientList.get(index).getNombre());
            clientDTO.setApellido(clientList.get(index).getApellido());
            clientDTO.setDni(clientList.get(index).getDni());
            clientDTOList.add(index,clientDTO);
        }
        log.info(GlobalValidator.getMethodName() + " -> Se obtienen todos los clientes de la BBDD.");
        return(clientDTOList);
    }

    public ClientDTO saveNewClient(ClientDTO newClientDTO) throws IllegalArgumentException {
        Client newClient = new Client();
        newClient.setNombre(newClientDTO.getNombre());
        newClient.setApellido(newClientDTO.getApellido());
        newClient.setDni(newClientDTO.getDni());
        if(this.clientRepository.existsByDniAndDeletedTrue(newClientDTO.getDni())){
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("deletedClientFilter");
            filter.setParameter("isDeleted", true);
            this.clientRepository.updateClientDeletedStatusForDni(false, newClientDTO.getDni());
            this.clientRepository.flush();
            session.disableFilter("deletedClientFilter");
            newClientDTO.setId(this.clientRepository.getIdByDni(newClientDTO.getDni()));
            log.info(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClientDTO.getDni() + " se ingresa a la BBDD.");
            return (newClientDTO);
        }
        if(this.clientRepository.existsByDni(newClientDTO.getDni())){
            log.info(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClientDTO.getDni() + " ya existe en la BBDD");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClient.getDni() +" ya existe en la BBDD.");
        }
        this.clientRepository.saveAndFlush(newClient);
        newClientDTO.setId(this.clientRepository.getLastById());
        log.info(GlobalValidator.getMethodName() + " -> El cliente con dni: " + newClientDTO.getDni() + " se ingresa a la BBDD.");
        return (newClientDTO);
    }

    public ClientDTO upgradeClientById(ClientDTO newClientDTO, Long id) {
        if(id <= 0){throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del cliente no es valido.");}
        Optional<Client> clientOptional = this.clientRepository.findByIdAndDni(id, newClientDTO.getDni());
        if(clientOptional.isEmpty()){
            log.error(GlobalValidator.getMethodName() + " -> El cliente con el id: " + id + " y dni: " + newClientDTO.getDni()+ " no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El cliente con el id: " + id + " y dni: " + newClientDTO.getDni()+ " no existe en la BBDD.");
        }
        Client existClient = clientOptional.get();
        existClient.setNombre(newClientDTO.getNombre());
        existClient.setApellido(newClientDTO.getApellido());
        existClient.setDni(newClientDTO.getDni());
        log.info(GlobalValidator.getMethodName() + " -> Se actualizan los datos del cliente con Id: " + id);
        this.clientRepository.saveAndFlush(existClient);
        return (newClientDTO);
    }

    public void deleteClientById(Long id) throws IllegalArgumentException {
        if(id <= 0){throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id del cliente no es valido.");}
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()) {
            log.error(GlobalValidator.getMethodName() + " -> El id: " + id + " del cliente no existe en la BBDD.");
            throw new IllegalArgumentException(GlobalValidator.getMethodName() + " -> El id" + id + " del cliente no existe en la BBDD.");
        }
        Client existsClient = clientOptional.get();
        log.info(GlobalValidator.getMethodName() + " -> El cliente con id: " + existsClient.getId() + " y dni: " + existsClient.getDni() +" fue borrado de la BBDD.");
        this.clientRepository.deleteById(id);
    }
}
