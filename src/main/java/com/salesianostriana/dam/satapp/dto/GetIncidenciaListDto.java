package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Incidencia;

import java.util.List;

public record GetIncidenciaListDto(
        Long count,
        List<GetIncidenciaDto> results
) {

    public static GetIncidenciaListDto of (List<Incidencia> incidencias) {

        return new GetIncidenciaListDto(
                (long) incidencias.size(),
                incidencias.stream()
                        .map(GetIncidenciaDto::of)
                        .toList()
        );
    }
}
