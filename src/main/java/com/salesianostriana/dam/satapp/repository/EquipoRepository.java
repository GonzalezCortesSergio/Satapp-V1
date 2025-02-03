package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    @Query("""
            SELECT e
            FROM Equipo e
            WHERE NOT EXISTS (
                SELECT i
                FROM Incidencia i
                WHERE i.equipo.id = e.id
                )
                OR EXISTS (
                    SELECT i
                    FROM Incidencia i
                    WHERE i.equipo.id = e.id
                    AND i.estado = 'CERRADA'
                )
            """)
    List<Equipo> findAllWithNoIncidencia();
}
