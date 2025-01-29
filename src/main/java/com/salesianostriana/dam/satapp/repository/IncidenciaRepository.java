package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Estado;
import com.salesianostriana.dam.satapp.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    @Query("""
            SELECT i
            FROM Incidencia i
            WHERE i.usuario.id = :idUsuario
            AND i.estado != :estado
            """)
    List<Incidencia> findAllByUsuario(Long idUsuario, Estado estado);


    @Query("""
            SELECT i
            FROM Incidencia i
            LEFT JOIN fetch i.tecnicosGestionan
            WHERE i.usuario.id = :idUsuario
            AND i.id = :idIncidencia
            """)
    Optional<Incidencia> findByUsuarioAndIdFetch(Long idUsuario, Long idIncidencia);
}
