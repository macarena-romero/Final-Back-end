package com.dh.clinica.controller;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.service.impl.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@Valid @RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?>  buscarPorId(@PathVariable Integer id) {

        return ResponseEntity.ok(pacienteService.buscarPorId(id).get());


    }
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>> buscarTodos(){

        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarPaciente(@Valid @RequestBody Paciente paciente){
            pacienteService.modificarPaciente(paciente);
            String jsonResponse = "{\"mensaje\": \"El paciente fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("{\"mensaje\": \"El paciente fue eliminado\"}");

    }
    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Paciente>> buscarApellidoYNombre(@RequestParam String apellido,
                                                                @RequestParam String nombre){
        return ResponseEntity.ok(pacienteService.buscarPorApellidoyNombre(apellido, nombre));
    }
    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Paciente>> buscarNombreLike(@PathVariable String nombre){
        return ResponseEntity.ok(pacienteService.buscarLikeNombre(nombre));
    }

}


