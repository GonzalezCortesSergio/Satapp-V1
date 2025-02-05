package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("""
            select c
            from Categoria c
            where upper(c.nombre) = upper(:nombre)
            """)
    Optional<Categoria> findByNombre(String nombre);

    @Modifying
    @Query("""
            update Categoria c
            set c.deleted = true
            where upper(c.nombre) = upper(:nombre)
            """)
    void deleteByNombre(String nombre);
}
