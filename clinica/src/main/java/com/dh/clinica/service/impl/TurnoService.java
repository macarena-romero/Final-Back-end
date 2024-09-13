package com.dh.clinica.service.impl;

import com.dh.clinica.dto.request.TurnoModificarDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.OdontologoResponseDto;
import com.dh.clinica.dto.response.PacienteResponseDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologService;
    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologService = odontologService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno();
        Turno turnoDesdeDb = null;
        TurnoResponseDto turnoARetornar = null;
        if (paciente.isPresent() && odontologo.isPresent()) {
            // mapear el turnoRequestDto a turno
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            // se persistir el turno
            turnoDesdeDb = turnoRepository.save(turno);
            logger.info("turno guardado " +turnoDesdeDb);

            // mapear el turnoDesdeDb a turnoResponseDto

            turnoARetornar = mapearATurnoResponse(turnoDesdeDb);
        } else{
            logger.warn("no se puede guardar el turno, el odontologo o el paciente no existen");
            throw new BadRequestException("El paciente o el odontologo no existen");

        }
        return turnoARetornar;
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turnoDesdeDb = turnoRepository.findById(id);
        TurnoResponseDto turnoResponseDto = null;
        if (turnoDesdeDb.isPresent()) {
            turnoResponseDto = mapearATurnoResponse(turnoDesdeDb.get());
            logger.info("turno encontrado");
        }else{
            logger.warn("el turno con ese id no se encuentra registrado");
            throw new ResourceNotFoundException("El turno "+ id +" no fue encontrado");
        }
        return Optional.ofNullable(turnoResponseDto);
    }


    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for (Turno t: turnos){
            TurnoResponseDto turnoAuxiliar = mapearATurnoResponse(t);
            logger.info("turnos "+t);
            turnosRespuesta.add(turnoAuxiliar);
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurno(TurnoModificarDto turnoModificarDto) {
        Optional<Turno> turnoEncontrado = turnoRepository.findById(turnoModificarDto.getId());
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModificarDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologService.buscarPorId(turnoModificarDto.getOdontologo_id());
        Turno turno = null;
        if (paciente.isPresent() && odontologo.isPresent () && turnoEncontrado.isPresent()) {
            turno = new Turno(turnoModificarDto.getId(), paciente.get(), odontologo.get(),
                    LocalDate.parse(turnoModificarDto.getFecha()));
                turnoRepository.save(turno);
                logger.info("el turno fue modificado");

            }else{
                logger.warn("El turno  no fue modificado");
                throw new ResourceNotFoundException("El turno  no fue modificado");
            }
        }


    @Override
    public void eliminarTurno(Integer id) {
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        if(turnoEncontrado.isPresent()){
            turnoRepository.deleteById(id);
            logger.info("el turno fue eliminado");
        }else{
            logger.warn("el turno no se puede eliminar porque no fue encontrado");
            throw new ResourceNotFoundException("El turno "+ id +" no fue encontrado");
        }

    }

    private TurnoResponseDto convertirTurnoAResponse(Turno turnoDesdeDb){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeDb.getOdontologo().getId(), turnoDesdeDb.getOdontologo().getMatricula(),
                turnoDesdeDb.getOdontologo().getNombre(), turnoDesdeDb.getOdontologo().getApellido()
        );

        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeDb.getPaciente().getId(), turnoDesdeDb.getPaciente().getNombre(),
                turnoDesdeDb.getPaciente().getApellido(), turnoDesdeDb.getPaciente().getDni()
        );

        TurnoResponseDto turnoARetornar = new TurnoResponseDto(
                turnoDesdeDb.getId(), pacienteResponseDto, odontologoResponseDto,
                turnoDesdeDb.getFecha().toString()
        );
        return turnoARetornar;
    }

    private TurnoResponseDto mapearATurnoResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }

    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente){
        logger.info("se esta buscando los turnos del paciente");
        return turnoRepository.buscarTurnoPorApellidoPaciente(apellidoPaciente);
    }

    @Override
    public List<TurnoResponseDto> buscarRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
        List<Turno> turnos = turnoRepository.rangoFechas(fechaInicial,fechaFinal);
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for (Turno t: turnos){
            TurnoResponseDto turnoAuxiliar = mapearATurnoResponse(t);
            logger.info("turnos "+t);
            turnosRespuesta.add(turnoAuxiliar);
        }
        return turnosRespuesta;

    }


}