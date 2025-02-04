package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Alumno;

import java.util.List;

public record GetAlumnoDto(
        String nombre,
        String username,
        List<GetHistoricoCursoDto> listaHistorico
) {

    public static GetAlumnoDto of(Alumno alumno){
        return new GetAlumnoDto(
                alumno.getNombre(),
                alumno.getUsername(),
                alumno.getListaHistoricoCurso().stream()
                        .map(GetHistoricoCursoDto::of)
                        .toList()

        );
    }

}
