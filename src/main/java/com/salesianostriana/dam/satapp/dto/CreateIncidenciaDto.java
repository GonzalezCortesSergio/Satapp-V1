package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Incidencia;

public record CreateIncidenciaDto(
        String titulo,
        String descripcion,
        int urgencia
) {

    public Incidencia toIncidencia() {

        return Incidencia.builder()
                .titulo(this.titulo)
                .descripcion(this.descripcion)
                .urgencia(this.urgencia)
                .build();
    }
}
