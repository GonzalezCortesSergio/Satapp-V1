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


    @Query("""
            SELECT i
            FROM Incidencia i
            LEFT JOIN fetch i.tecnicosGestionan
            WHERE i.usuario.id = :idUsuario
            AND i.id = :idIncidencia
            AND i.estado IN ('ABIERTA', 'PENDIENTE')
            """)
    Optional<Incidencia> findByUsuarioAndIdAbiertaOrPendiente(Long idUsuario, Long idIncidencia);

    @Query("""
            SELECT i
            FROM Incidencia i
            WHERE i.usuario.id = :idUsuario
            AND i.id = :idIncidencia
            AND i.estado != 'CERRADA'
            """)
    Optional<Incidencia> findByUsuarioAndIdNotCerrada(Long idUsuario, Long idIncidencia);


    @Query("""
            SELECT i
            FROM Incidencia i
            ORDER BY i.urgencia DESC
            """)
    List<Incidencia> findAll();


    @Query("""
            SELECT i
            FROM Incidencia i
            ORDER BY i.urgencia DESC, i.fecha DESC
            """)
    List<Incidencia> findAllOrderByFecha();


    @Query("""
            SELECT i
            FROM Incidencia i
            WHERE i.categoria.nombre = :nombreCategoria
            ORDER BY i.urgencia DESC
            """)
    List<Incidencia> findAllByCategoriaNombre(String nombreCategoria);


    @Query("""
            SELECT i
            FROM Incidencia i
            WHERE CAST(i.estado AS string) = :estado
            ORDER BY i.urgencia DESC
            """)
    List<Incidencia> findAllByEstado(String estado);


    @Query("""
            SELECT i
            FROM Incidencia i
            WHERE i.ubicacion.nombre = :nombreUbicacion
            ORDER BY i.urgencia DESC
            """)
    List<Incidencia> findAllByUbicacion(String nombreUbicacion);
}
