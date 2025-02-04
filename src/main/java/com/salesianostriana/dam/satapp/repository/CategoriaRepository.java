package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("""
            select c
            from Categoria c
            where c.nombre = :nombre
            """)
    Optional<Categoria> findByNombre(String nombre);

}
