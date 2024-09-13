package com.dh.clinica.service;

import com.dh.clinica.entity.Odontologo;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    Odontologo guardarOdontologo (Odontologo odontologo);

    Optional<Odontologo> buscarPorId(Integer id);

     List<Odontologo> buscarTodos();


    Optional<Odontologo> modificarOdontologo(Odontologo odontologo);


    void eliminarOdontologo(Integer id);

    List<Odontologo> BuscarTodosOrdenApellido();


}

