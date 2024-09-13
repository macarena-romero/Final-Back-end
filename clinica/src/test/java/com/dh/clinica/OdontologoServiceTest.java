package com.dh.clinica;


import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.service.impl.OdontologoService;
import com.dh.clinica.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class OdontologoServiceTest {
    @Autowired
    OdontologoService odontologoService;

    Odontologo odontologo;
    Odontologo odontologoDesdeDb;

    @BeforeEach
    void crearOdontologo(){

        odontologo = new Odontologo();
        odontologo.setMatricula("2154");
        odontologo.setNombre("juan");
        odontologo.setApellido("Perez");
        odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
    }


    @Test
    @DisplayName("Testear que un odontologo se guarde en la base de datos ")
    void caso1(){

        assertNotNull(odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear que un odontologo pueda ser obtenido cuando se envia el id")
    void caso2(){
        //dado
        Integer id = odontologoDesdeDb.getId();
        // cuando
        Odontologo odontologoEncontrado = odontologoService.buscarPorId(id).get();
        // entonces
        assertEquals(id, odontologoEncontrado.getId());
    }

}