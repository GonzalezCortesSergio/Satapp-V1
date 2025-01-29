package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Alumno;
import com.salesianostriana.dam.satapp.model.Personal;
import com.salesianostriana.dam.satapp.model.Tecnico;

public record CreateUsuarioDto(
        String nombre,
        String username,
        String password,
        String email,
        String role
) {
    //api/usuario/admin/{idAdmin}/crear/{tipoUsuario}?PROFESOR
    ///api/usuario/admin/1/crear/personal?tipo=PROFESOR
    public Alumno toAlumno(){
        return Alumno.builder()
                .nombre(this.nombre)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .build();
    }

    public Personal toPersonal(){
        return Personal.builder()
                .nombre(this.nombre)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .build();
    }

    public Tecnico toTecnico(){
        return Tecnico.builder()
                .nombre(this.nombre)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .build();
    }
}
