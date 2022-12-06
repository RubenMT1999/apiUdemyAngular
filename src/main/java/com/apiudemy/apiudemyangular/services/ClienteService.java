package com.apiudemy.apiudemyangular.services;

import com.apiudemy.apiudemyangular.models.Cliente;
import com.apiudemy.apiudemyangular.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService{

    @Autowired
    IClienteRepository clienteRepository;


    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Integer id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }
}
