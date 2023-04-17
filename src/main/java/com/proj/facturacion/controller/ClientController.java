package com.proj.facturacion.controller;

import com.proj.facturacion.model.Client;
import com.proj.facturacion.model.ClientDTO;
import com.proj.facturacion.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/clients")
public class ClientController {
    @Autowired
    public ClientService clientService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        return (new ResponseEntity<>(this.clientService.getClientById(id), HttpStatus.OK));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<ClientDTO>> findAll() {
        return (new ResponseEntity<>(this.clientService.getAllClients(),HttpStatus.OK));
    }

    @PostMapping(path = "/")
    public ResponseEntity<ClientDTO> saveNewClient(@Valid @RequestBody ClientDTO clientDTO) {
        return (new ResponseEntity<>(this.clientService.saveNewClient(clientDTO), HttpStatus.OK));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ClientDTO> updateClientsById(@RequestBody ClientDTO clientDTO, @PathVariable Long id) {
        return (new ResponseEntity<>(this.clientService.upgradeClientById(clientDTO, id), HttpStatus.OK));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteClientsById(@PathVariable Long id) throws Exception {
        this.clientService.deleteClientById(id);
    }
}
