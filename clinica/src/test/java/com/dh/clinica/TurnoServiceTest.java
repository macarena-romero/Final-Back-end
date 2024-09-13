package com.dh.clinica;


import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.OdontologoResponseDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.impl.OdontologoService;
import com.dh.clinica.service.impl.PacienteService;
import com.dh.clinica.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class TurnoServiceTest {
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    TurnoService turnoService;

    Odontologo odontologo;

    Paciente paciente;
    TurnoRequestDto turno;
    Odontologo odontologoDesdeDB;
    Paciente pacienteDesdeDb;
    TurnoResponseDto turnoDesdeDB;

    @BeforeEach
    void crearPaciente(){
        Domicilio domicilio = new Domicilio(null,"Falsa", 456, "Cipolleti", "Rio Negro");
        paciente = new Paciente();
        paciente.setApellido("Romero");
        paciente.setNombre("Luciana");
        paciente.setDni("56655555");
        paciente.setFechaIngreso(LocalDate.of(2024, 7, 16));
        paciente.setDomicilio(domicilio);
        pacienteDesdeDb = pacienteService.guardarPaciente(paciente);
    }
    @BeforeEach
    void crearOdontologo(){
        odontologo = new Odontologo();
        odontologo.setMatricula("2154");
        odontologo.setNombre("juan");
        odontologo.setApellido("Perez");
        odontologoDesdeDB = odontologoService.guardarOdontologo(odontologo);
    }
    @BeforeEach
    void crearTurno(){

        turno = new TurnoRequestDto();
        turno.setPaciente_id(pacienteDesdeDb.getId());
        turno.setOdontologo_id(odontologoDesdeDB.getId());
        turno.setFecha("2024-05-12");
        turnoDesdeDB = turnoService.guardarTurno(turno);

    }


    @Test
    @DisplayName("Testear que un turno se guarde en la base de datos")
    void caso1(){
        //dado
        // cuando
        // entonces
        assertNotNull(turnoDesdeDB.getId());
    }

    @Test
    @DisplayName("Testear que un turno pueda ser obtenido cuando se envia el id")
    void caso2(){
        //dado
        Integer id = turnoDesdeDB.getId();
        // cuando
        TurnoResponseDto turnoEncontrado = turnoService.buscarPorId(id).get();
        // entonces
        assertEquals(id, turnoEncontrado.getId());
    }

}