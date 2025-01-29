package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Incidencia;

import java.time.LocalDate;

public record GetIncidenciaDto(
        Long id,
        String titulo,
        String descripcion,
        int urgencia
) {

    public static GetIncidenciaDto of (Incidencia incidencia) {

        return new GetIncidenciaDto(
                incidencia.getId(),
                incidencia.getTitulo(),
                incidencia.getDescripcion(),
                incidencia.getUrgencia()
        );
    }
}
