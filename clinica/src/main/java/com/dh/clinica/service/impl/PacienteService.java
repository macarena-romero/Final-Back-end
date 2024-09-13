package com.dh.clinica.service.impl;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        if (paciente == null){
            logger.warn("el paciente no puede ser nulo");
            throw new BadRequestException("El paciente no puede ser nulo");

        }else{
            logger.info("paciente guardado");
            return pacienteRepository.save(paciente);

        }

    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        Optional<Paciente> pacienteDesdeDB = pacienteRepository.findById(id);
        if (pacienteDesdeDB.isPresent()){
            logger.info("el paciente con el id "+ id + "  fue encontrado");
        }else{
            logger.warn("el paciente con ese id no se encuentra registrado");
            throw new ResourceNotFoundException("El paciente "+ id +" no fue encontrado");
        }
        return pacienteDesdeDB;
    }


    @Override
    public List<Paciente> buscarTodos() {
        logger.info("se estan buscando todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> modificarPaciente(Paciente paciente) {
        Optional<Paciente> pacienteDesdeDB = pacienteRepository.findById(paciente.getId());
        if (pacienteDesdeDB.isPresent()){
            logger.info("el paciente con el id "+ paciente.getId() + "  fue modificado");
            pacienteRepository.save(paciente);
        }else{
            logger.warn("el paciente con ese id no se pudo modificar");
            throw new ResourceNotFoundException("El paciente "+ paciente.getId()+" no fue modificado");
        }
        return pacienteDesdeDB;
    }


    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);
        if(pacienteEncontrado.isPresent()){
            pacienteRepository.deleteById(id);
            logger.info("el paciente fue eliminado");

        }else{
            logger.warn("no se pudo eliminar el paciente " + id);
            throw new ResourceNotFoundException("El paciente "+ id +" no fue encontrado");

        }

    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        logger.info("se esta buscando por nombre y apellido");
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarLikeNombre(String nombre) {
        logger.info("se esta buscando el paciente por nombre");

        return pacienteRepository.findByNombreLike(nombre);
    }
}