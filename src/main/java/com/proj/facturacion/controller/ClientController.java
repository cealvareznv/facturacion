package com.proj.facturacion.controller;

import com.proj.facturacion.model.Client;
import com.proj.facturacion.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/clients")
public class ClientController {
    @Autowired
    public ClientService clientService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) throws Exception{
        return (new ResponseEntity<>(this.clientService.getClientById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Client>> findAll() {
        return (new ResponseEntity<>(this.clientService.getAllClients(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Client> saveNewClient(@RequestBody Client client) throws Exception {
        return(new ResponseEntity<>(this.clientService.saveNewClient(client), HttpStatus.OK));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Client> updateClientsById(@RequestBody Client client, @PathVariable Long id) throws Exception {
        return (new ResponseEntity<>(this.clientService.upgradeClientById(client, id), HttpStatus.OK));
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteClientsById(@PathVariable Long id) throws Exception {
        return (this.clientService.deleteClientById(id));
    }
}
