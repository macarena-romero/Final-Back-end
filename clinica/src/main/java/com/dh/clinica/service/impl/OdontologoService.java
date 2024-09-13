package com.dh.clinica.service.impl;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private final Logger logger = LoggerFactory.getLogger(OdontologoService.class);
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        if (odontologo == null){
            logger.warn("el odontologo no puede ser nulo");
            throw new BadRequestException("El odontologo no puede ser nulo");

        }else{
            logger.info("odontologo guardado");
            return odontologoRepository.save(odontologo);
        }
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologoDesdeDB= odontologoRepository.findById(id);
        if (odontologoDesdeDB.isPresent()){
            logger.info("el odontologo con el id "+ id + "  fue encontrado");
        }else{
            logger.warn("el odontologo con ese id no se encuentra registrado");
            throw new ResourceNotFoundException("El odontologo"+ id +" no fue encontrado");
        }
        return odontologoDesdeDB;

    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info("se estan buscando todos los odontologos");
        return odontologoRepository.findAll();
    }

    @Override
    public Optional<Odontologo> modificarOdontologo(Odontologo odontologo) {
        Optional<Odontologo> odontologoDesdeDB= odontologoRepository.findById(odontologo.getId());
        if (odontologoDesdeDB.isPresent()){
            logger.info("el odontologo con el id "+ odontologo.getId()+ "  fue modificado");
            odontologoRepository.save(odontologo);
        }else{
            logger.warn("el odontologo con ese id no se encuentra registrado");
            throw new ResourceNotFoundException("El odontologo "+ odontologo.getId() +" no fue modificado");
        }
        return odontologoDesdeDB;

    }


    @Override
    public void eliminarOdontologo(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);
        if(odontologoEncontrado.isPresent()){
            odontologoRepository.deleteById(id);
            logger.info("el odontologo fue eliminado");
        }else{
            logger.warn("no se pudo eliminar el odontologo" + id);
            throw new ResourceNotFoundException("El odontologo"+ id +" no fue encontrado");
        }


    }

    @Override
    public List<Odontologo> BuscarTodosOrdenApellido() {
        logger.info("se estan buscando los odontologos en orden de apellido");

        return odontologoRepository.OrderByapellidoDESC();
    }


}
