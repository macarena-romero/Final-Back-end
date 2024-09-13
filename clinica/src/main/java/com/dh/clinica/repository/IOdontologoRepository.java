package com.dh.clinica.repository;

import com.dh.clinica.entity.Odontologo;

import com.dh.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
    @Query("select o from Odontologo o order by o.apellido")
    List<Odontologo> OrderByapellidoDESC();

}
