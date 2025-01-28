package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Incidencia;
import com.salesianostriana.dam.satapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    @Query("""
            SELECT in
            FROM Incidencia in
            WHERE in.usuario = :usuario
            """)
    List<Incidencia> findByUsuario(@Param("usuario") Usuario usuario);
}
