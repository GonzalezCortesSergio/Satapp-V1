package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.IncidenciaTecnico;

public record GetIncidenciaTecnicoDto(
        GetIncidenciaDetailsDto incidencia,
        String tecnicoResponsable
) {

    public static GetIncidenciaTecnicoDto of (IncidenciaTecnico it) {

        return new GetIncidenciaTecnicoDto(
                GetIncidenciaDetailsDto.of(it.getIncidencia()),
                it.getTecnico().getNombre()

        );
    }
}
