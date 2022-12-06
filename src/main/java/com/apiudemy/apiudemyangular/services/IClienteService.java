package com.apiudemy.apiudemyangular.services;

import com.apiudemy.apiudemyangular.models.Cliente;

import java.util.List;

public interface IClienteService {


    public List<Cliente> findAll();

    public Cliente save(Cliente cliente);

    public void delete(Integer id);

    public Cliente findById(Integer id);

}
