package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Alumno;
import com.salesianostriana.dam.satapp.model.Personal;
import com.salesianostriana.dam.satapp.model.Tipo;
import com.salesianostriana.dam.satapp.model.Usuario;

public record GetUsuarioDto(
        String nombre,
        String username,
        String email,
        String role,
        String tipoUsuario
) {

    public static GetUsuarioDto of(Usuario usuario){

        String tipoUsuario = "técnico";

        if (usuario instanceof Alumno) {
            tipoUsuario = "alumno";

            return new GetUsuarioDto(usuario.getNombre(), usuario.getUsername(),
                    usuario.getEmail(), usuario.getRole(), tipoUsuario
            );

        }

        if (usuario instanceof Personal) {
            tipoUsuario = "personal";

            return new GetUsuarioDto(usuario.getNombre(), usuario.getUsername(),
                    usuario.getEmail(), usuario.getRole(), tipoUsuario
            );

        }

        return new GetUsuarioDto(usuario.getNombre(), usuario.getUsername(),
                usuario.getEmail(), usuario.getRole(), tipoUsuario
        );

    }

}
