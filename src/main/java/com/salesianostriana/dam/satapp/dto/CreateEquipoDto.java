package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Equipo;

public record CreateEquipoDto(
        String nombre,
        String caracteristicas
) {

    public Equipo toEquipo() {

        return Equipo.builder()
                .nombre(this.nombre)
                .caracteristicas(this.caracteristicas)
                .build();
    }
}
