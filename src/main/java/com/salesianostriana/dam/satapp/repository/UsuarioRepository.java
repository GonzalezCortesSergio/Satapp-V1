package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Personal;
import com.salesianostriana.dam.satapp.model.Tipo;
import com.salesianostriana.dam.satapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
            select u
            from Usuario u
            where u.id = :idUsuario
            and type(u) = 'personal'
            and u.tipo = :tipo
            """)
    Optional<Personal> findByIdPas(@Param("idUsuario") Long id, @Param("tipo") Tipo tipo);

}
