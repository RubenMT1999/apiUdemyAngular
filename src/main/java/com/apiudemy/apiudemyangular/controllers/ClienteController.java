package com.apiudemy.apiudemyangular.controllers;

import com.apiudemy.apiudemyangular.models.Cliente;
import com.apiudemy.apiudemyangular.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    IClienteService clienteService;


    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    //usamos ResponseEntity para manejar la respuesta en caso de que exista un error
    //por que el id no exista
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id){

        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try{
            cliente = clienteService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if(cliente == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat("no existe en la bbdd")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){

        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
//            List<String> errors = new ArrayList<>();
//
//            for (FieldError err: result.getFieldErrors()){
//                errors.add("El campo '"+ err.getField() +"' "+ err.getDefaultMessage());
//            }
            List<String> errors = result.getFieldErrors()
                            .stream().map(err -> {
                        return "El campo '"+ err.getField() +"' "+ err.getDefaultMessage();
                    }).collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

        try{
            clienteNew = clienteService.save(cliente);
        }catch (DataAccessException e){
            response.put("mensaje","Error al realizar el insert en la BBDD");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido creado con éxito");
        response.put("cliente",clienteNew);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }



    @PutMapping("clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Integer id){

        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream().map(err -> {
                        return "El campo '"+ err.getField() +"' "+ err.getDefaultMessage();
                    }).collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

        if(clienteActual == null){
            response.put("mensaje", "Error, no se puedo editar. El cliente ID: ".concat(id.toString().concat("no existe en la bbdd")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        try{
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setEmail(cliente.getEmail());
        clienteUpdated = clienteService.save(clienteActual);

        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar cliente en la BBDD");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido actualizado con éxito");
        response.put("cliente",clienteUpdated);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();

        try{
            clienteService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al eliminar cliente en la BBDD");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Cliente eliminado con éxito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }



}
