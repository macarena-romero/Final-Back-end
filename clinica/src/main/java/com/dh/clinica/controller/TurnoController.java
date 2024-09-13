package com.dh.clinica.controller;

import com.dh.clinica.dto.request.TurnoModificarDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.impl.TurnoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {

        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarTurno(@Valid @RequestBody TurnoRequestDto turnoRequestDto){
        TurnoResponseDto turnoAGuardar = turnoService.guardarTurno(turnoRequestDto);
        //if(turnoAGuardar != null){
            return ResponseEntity.ok("{\"mensaje\": \"El turno fue guardado\"}");
        //} else {
          //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente o el odontologo no fueron encontrados");
        }


    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?>  buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(turnoService.buscarPorId(id).get());

    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarTurno(@Valid @RequestBody TurnoModificarDto turnoModificarDto){
            turnoService.modificarTurno(turnoModificarDto);
            return ResponseEntity.ok("{\"mensaje\": \"El turno fue modificado\"}");

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Integer id){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("{\"mensaje\": \"El turno fue eliminado\"}");
    }

    @GetMapping("/buscartodos/{apellido}")
    public ResponseEntity<List<Turno>> buscarTurnoApellidoPaciente(@PathVariable String apellido){
        return ResponseEntity.ok(turnoService.buscarTurnoPaciente(apellido));
    }
    @GetMapping("/busquedarango")
    public ResponseEntity<List<TurnoResponseDto>> buscarPorRango (@RequestParam LocalDate fechaInicial,
                                                       @RequestParam LocalDate fechaFinal){
        return ResponseEntity.ok(turnoService.buscarRangoFechas(fechaInicial,fechaFinal));
    }
}
