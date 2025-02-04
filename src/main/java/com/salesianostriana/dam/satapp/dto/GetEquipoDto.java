package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Equipo;

public record GetEquipoDto(
        Long id,
        String nombre,
        String caracteristicas,
        String ubicacion
) {

    public static GetEquipoDto of (Equipo equipo) {

        if(equipo.getUbicacion() == null) {

            return new GetEquipoDto(
                    equipo.getId(),
                    equipo.getNombre(),
                    equipo.getCaracteristicas(),
                    " "
            );
        }

        return new GetEquipoDto(
                equipo.getId(),
                equipo.getNombre(),
                equipo.getCaracteristicas(),
                equipo.getUbicacion().getNombre()
        );

    }
}
