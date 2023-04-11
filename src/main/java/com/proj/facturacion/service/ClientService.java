package com.proj.facturacion.service;

import com.proj.facturacion.model.Client;
import com.proj.facturacion.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ClientService {
    final int lenghtDocNumber = 8;
    @Autowired
    public ClientRepository clientRepository;

    public Client getClientById(Long id)throws Exception{
        if(id <= 0) {throw new Exception("getClientById -> El id del cliente no existe en la BBDD.");}
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            log.info("getClientById -> El id del cliente no existe en la BBDD.");
            throw new Exception("getClientById -> El id del cliente no existe en la BBDD.");
        }else{
            Client objClient = clientOptional.get();
            log.info("getClientById -> Se obtiene el cliente con el id: " + id);
            return (objClient);
        }
    }

    public List<Client> getAllClients(){
        log.info("getAllClients -> Se obtienen todos los clientes");
        return(this.clientRepository.findAll());
    }

    public Client saveNewClient(Client newClient) throws Exception {
        Optional<Client> clientOptional = this.clientRepository.findByDni(newClient.getDni());
        if (clientOptional.isPresent()) {
            log.info("getClientById -> El numero de documento " + newClient.getDni() + " ya existe en la BBDD y pertene a: "  +
                    newClient.getApellido() + " " + newClient.getNombre());
            throw new Exception("getClientById -> El numero de documento " + newClient.getDni() +" ya existe en la BBDD.");
        } else {
            if(newClient.getDni().length() < lenghtDocNumber) {
                log.info("getClientById -> El numero de documento " + newClient + " no es valido.");
                throw new Exception("getClientById -> El numero de documento no es valido.");
            } else {
                log.info("getClientById -> Cliente " + newClient.getNombre() + " " + newClient.getApellido() + " con DNI: "
                        + newClient.getDni() + " se ingresa a la BBDD.");
                return (this.clientRepository.saveAndFlush(newClient));
            }
        }
    }

    public Client upgradeClientById(Client newClient, Long id) throws Exception {
        if(id <= 0){ throw new Exception("upgradeClientById -> El id del cliente no existe en la BBDD."); }
        if(Objects.isNull(newClient.getNombre()) || "".equalsIgnoreCase(newClient.getNombre())){
            throw new Exception("upgradeClientById -> El nombre del cliente no es valido.");
        }
        if(Objects.isNull(newClient.getApellido()) || "".equalsIgnoreCase(newClient.getApellido())){
            throw new Exception("upgradeClientById -> El apellido del cliente no es valido.");
        }
        if(Objects.isNull(newClient.getDni()) || "".equalsIgnoreCase(newClient.getDni())){
            throw new Exception("upgradeClientById -> El cliente no tiene un numero de documento valido.");
        }
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()) {
            log.info("upgradeClientById -> El cliente no existe en la BBDD.");
            throw new Exception("upgradeClientById -> El cliente no existe en la BBDD.");
        }else{
            log.info("upgradeClientById -> Se actualizan los datos del cliente con Id: " + id);
            Client existClient = clientOptional.get();
            existClient.setNombre(newClient.getNombre());
            existClient.setApellido(newClient.getApellido());
            existClient.setDni(newClient.getDni());
            return(this.clientRepository.saveAndFlush(existClient));
        }
    }

    public Boolean deleteClientById(Long id) throws Exception {
        Boolean deleteClient = false;
        if(id <= 0) { throw new Exception("deleteClientById -> El id del cliente no existe en la BBDD."); }
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()) {
            log.info("deleteClientById -> El id" + id + " del cliente no existe en la BBDD.");
        }else {
            Client existClient = clientOptional.get();
            log.info("deleteClientById -> El cliente con Id: " +existClient.getId() + " nombre: " +
                    existClient.getNombre() + " apellido: " + existClient.getApellido() + " fue borrado de la BBDD.");
            this.clientRepository.delete(existClient);
            deleteClient = true;
        }
        return (deleteClient);
    }
}
