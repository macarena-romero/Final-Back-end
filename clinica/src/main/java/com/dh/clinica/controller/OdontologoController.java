package com.dh.clinica.controller;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.service.impl.OdontologoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> guardarOdontologo(@Valid @RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar/{id}")

    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        //Optional<Odontologo> odontologo = odontologoService.buscarPorId(id);
        return ResponseEntity.ok(odontologoService.buscarPorId(id).get());
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> buscarTodos() {

        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarOdontologo(@Valid @RequestBody Odontologo odontologo) {
           // return ResponseEntity.ok(odontologoService.modificarOdontologo(odontologo));
        odontologoService.modificarOdontologo(odontologo);
        String jsonResponse = "{\"mensaje\": \"El odontologo fue modificado\"}";
        return ResponseEntity.ok(jsonResponse);
    }


    @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<?> eliminarOdontologo (@PathVariable Integer id){
                odontologoService.eliminarOdontologo(id);
                return ResponseEntity.ok("{\"mensaje\": \"El odontologo fue eliminado\"}");

        }

    @GetMapping("/buscarOrdenados")
    public ResponseEntity<List<Odontologo>>buscarOrdenados(){
        return ResponseEntity.ok(odontologoService.BuscarTodosOrdenApellido());
    }

    }

