package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    List<Incidencia> findByUsuario(@Param(""));
}
