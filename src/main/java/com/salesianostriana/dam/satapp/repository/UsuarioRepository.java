package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
