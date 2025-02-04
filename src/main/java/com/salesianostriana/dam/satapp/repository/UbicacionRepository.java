package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {

    @Query("""
            SELECT u
            FROM Ubicacion u
            WHERE u.nombre = :nombre
            """)
    Optional<Ubicacion> findByNombre(String nombre);

    @Modifying
    @Query("""
            UPDATE ubicacion SET deleted = true WHERE id= :nombre
            """)
    void deleteByNombre(String nombre);
}
