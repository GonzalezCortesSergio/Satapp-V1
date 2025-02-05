package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.IncidenciaTecnico;
import com.salesianostriana.dam.satapp.model.IncidenciaTecnicoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IncidenciaTecnicoRepository extends JpaRepository<IncidenciaTecnico, IncidenciaTecnicoPK> {

    @Query("""
            SELECT it
            FROM IncidenciaTecnico it
            WHERE it.incidencia.id = :idIncidencia
            """)
    List<IncidenciaTecnico> findByIdIncidenciaTecnicoResponsable(Long idIncidencia);

    @Query("""
            SELECT it
            FROM IncidenciaTecnico it
            WHERE it.incidencia.id = :idIncidencia
            AND it.tecnico.id = :idTecnico
            """)
    Optional<IncidenciaTecnico> findByIdIncidenciaAndIdTecnico(Long idIncidencia, Long idTecnico);

    @Query("""
            SELECT it
            FROM IncidenciaTecnico it
            WHERE it.incidencia.id = :idIncidencia
            AND it.tecnico.id = :idTecnico
            AND it.tecnicoResponsable IS TRUE
            """)
    Optional<IncidenciaTecnico> findByIdIncidenciaAndIdTecnicoResponsable(Long idIncidencia, Long idTecnico);
}
