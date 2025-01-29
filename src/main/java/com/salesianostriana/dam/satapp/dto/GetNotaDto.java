package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Nota;

import java.time.LocalDate;

public record GetNotaDto(
        Long id,
        LocalDate fecha,
        String contenido,
        String autor
) {

    public static GetNotaDto of (Nota nota) {

        return new GetNotaDto(
                nota.getId(),
                nota.getFecha(),
                nota.getContenido(),
                nota.getAutor()
        );
    }
}
