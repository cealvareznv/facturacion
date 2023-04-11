package com.proj.facturacion.controller;

import com.proj.facturacion.model.Clients;
import com.proj.facturacion.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/clients")
public class ClientsController {
    @Autowired
    public ClientsService clientsService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Clients> findById(@PathVariable Long id) throws Exception{
        return (new ResponseEntity<>(this.clientsService.getClientsById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Clients>> findAll() {
        return (new ResponseEntity<>(this.clientsService.getAllClients(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Clients> saveNewClients(@RequestBody Clients client) throws Exception {
        return(new ResponseEntity<>(this.clientsService.saveNewClients(client), HttpStatus.OK));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Clients> updateClientsById(@RequestBody Clients client, @PathVariable Long id) throws Exception {
        return (new ResponseEntity<>(this.clientsService.upgradeClientsById(client, id), HttpStatus.OK));
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteClientsById(@PathVariable Long id) throws Exception {
        return (this.clientsService.deleteClientsById(id));
    }
}
