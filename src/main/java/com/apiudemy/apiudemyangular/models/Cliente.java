package com.apiudemy.apiudemyangular.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "clientes")
public class Cliente {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "no puede estar vacío")
    @Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "no puede estar vacío")
    private String apellido;

    @NotEmpty(message = "no puede estar vacío")
    @Email(message = "no es una dirección de correo bien formada")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    //antes de que se cree el objeto le asignará el valor
    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }


}
