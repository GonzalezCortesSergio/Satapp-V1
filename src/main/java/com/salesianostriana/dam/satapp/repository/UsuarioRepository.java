package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Personal;
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
            and u.tipo = 'PAS'
            """)
    Optional<Personal> findByIdPas(@Param("idUsuario") Long id);

    @Query("""
        select u
        from Usuario u
        where u.id = :id
        and (
            u.id = :idUsuario
            or (select u2.tipo from Usuario u2 where u2.id = :idUsuario) = 'PAS'
        )
    """)
    Optional<Usuario> findByIdPropio(@Param("id") Long id, @Param("idUsuario") Long idUsuario);



}
