package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Equipo;

public record GetEquipoDto(
        Long id,
        String nombre,
        String caracteristicas
) {

    public static GetEquipoDto of (Equipo equipo) {

        return new GetEquipoDto(
                equipo.getId(),
                equipo.getNombre(),
                equipo.getCaracteristicas()
        );
    }
}
