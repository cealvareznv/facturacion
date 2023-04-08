package com.proj.facturacion.service;

import com.proj.facturacion.model.Clients;
import com.proj.facturacion.model.Invoice;
import com.proj.facturacion.repository.ClientsRepository;
import com.proj.facturacion.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ClientsService {
    final int lenghtDocNumber = 8;
    @Autowired
    public ClientsRepository clientsRepository;

    public Clients getClientsById(Long id)throws Exception{
        if(id <= 0) {throw new Exception("getClientsById -> El id del cliente no existe en la BBDD.");}
        Optional<Clients> clientsOptional = this.clientsRepository.findById(id);
        if(clientsOptional.isEmpty()){
            log.info("getClientsById -> El id del cliente no existe en la BBDD.");
            throw new Exception("getClientsById -> El id del cliente no existe en la BBDD.");
        }else{
            Clients objClients = clientsOptional.get();
            log.info("getClientsById -> Se obtiene el cliente con el id: " + id);
            return (objClients);
        }
    }

    public List<Clients> getAllClients(){
        log.info("getAllClients -> Se obtienen todos los clientes");
        return(this.clientsRepository.findAll());
    }

    public Clients saveNewClients(Clients newClient) throws Exception {
        Optional<Clients> clientsOptional = this.clientsRepository.findByDni(newClient.getDni());
        if (clientsOptional.isPresent()) {
            log.info("saveNewClients -> El numero de documento " + newClient.getDni() + " ya existe en la BBDD y pertene a: "  +
                    newClient.getApellido() + " " + newClient.getNombre());
            throw new Exception("saveNewClients -> El numero de documento " + newClient.getDni() +" ya existe en la BBDD.");
        } else {
            if(newClient.getDni().length() < lenghtDocNumber) {
                log.info("saveNewClients -> El numero de documento " + newClient + " no es valido.");
                throw new Exception("saveNewClients -> El numero de documento no es valido.");
            } else {
                log.info("saveNewClients -> Cliente " + newClient.getNombre() + " " + newClient.getApellido() + " con DNI: "
                        + newClient.getDni() + " se ingresa a la BBDD.");
                return (this.clientsRepository.saveAndFlush(newClient));
            }
        }
    }

    public Clients upgradeClientsById(Clients newClient, Long id) throws Exception {
        if(id <= 0){ throw new Exception("upgradeClientsById -> El id del cliente no existe en la BBDD."); }
        if(Objects.isNull(newClient.getNombre()) || "".equalsIgnoreCase(newClient.getNombre())){
            throw new Exception("upgradeClientsById -> El nombre del cliente no es valido.");
        }
        if(Objects.isNull(newClient.getApellido()) || "".equalsIgnoreCase(newClient.getApellido())){
            throw new Exception("upgradeClientsById -> El apellido del cliente no es valido.");
        }
        if(Objects.isNull(newClient.getDni()) || "".equalsIgnoreCase(newClient.getDni())){
            throw new Exception("upgradeClientsById -> El cliente no tiene un numero de documento valido.");
        }
        Optional<Clients> clientsOptional = this.clientsRepository.findById(id);
        if(clientsOptional.isEmpty()) {
            log.info("upgradeClientsById -> El cliente no existe en la BBDD.");
            throw new Exception("upgradeClientsById -> El cliente no existe en la BBDD.");
        }else{
            log.info("upgradeClientsById -> Se actualizan los datos del cliente con Id: " + id);
            Clients existClient = clientsOptional.get();
            existClient.setNombre(newClient.getNombre());
            existClient.setApellido(newClient.getApellido());
            existClient.setDni(newClient.getDni());
            return(this.clientsRepository.saveAndFlush(existClient));
        }
    }

    public Boolean deleteClientsById(Long id) throws Exception {
        Boolean deleteClients = false;
        if(id <= 0) { throw new Exception("upgradeClientsById -> El id del cliente no existe en la BBDD."); }
        Optional<Clients> clientsOptional = this.clientsRepository.findById(id);
        if(clientsOptional.isEmpty()) {
            log.info("upgradeClientsById -> El id" + id + " del cliente no existe en la BBDD.");
        }else {
            Clients existClient = clientsOptional.get();
            log.info("upgradeClientsById -> El cliente con Id: " +existClient.getId() + " nombre: " +
                    existClient.getNombre() + " apellido: " + existClient.getApellido() + " fue borrado de la BBDD.");
            this.clientsRepository.delete(existClient);
            deleteClients = true;
        }
        return (deleteClients);
    }
}
