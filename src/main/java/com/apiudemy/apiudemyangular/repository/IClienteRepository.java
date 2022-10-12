package com.apiudemy.apiudemyangular.repository;

import com.apiudemy.apiudemyangular.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Integer> {
}
