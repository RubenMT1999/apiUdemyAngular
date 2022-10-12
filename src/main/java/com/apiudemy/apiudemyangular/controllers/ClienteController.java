package com.apiudemy.apiudemyangular.controllers;

import com.apiudemy.apiudemyangular.models.Cliente;
import com.apiudemy.apiudemyangular.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    IClienteService clienteService;


    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }


}
