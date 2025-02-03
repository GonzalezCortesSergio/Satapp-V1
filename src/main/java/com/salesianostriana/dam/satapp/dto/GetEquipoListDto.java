package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Equipo;

import java.util.List;

public record GetEquipoListDto(
        Long count,
        List<GetEquipoDto> results
) {

    public static GetEquipoListDto of (List<Equipo> equipos) {

        return new GetEquipoListDto(
                (long) equipos.size(),
                equipos.stream()
                        .map(GetEquipoDto::of)
                        .toList()
        );
    }
}
