package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Equipo;
import com.salesianostriana.dam.satapp.model.Incidencia;
import com.salesianostriana.dam.satapp.model.Ubicacion;

import java.time.LocalDate;
import java.util.List;

public record GetIncidenciaDetailsDto(
    LocalDate fecha,
    String titulo,
    String descripcion,
    String estado,
    int urgencia,
    String categoria,
    List<GetNotaDto> notas,
    Equipo equipo,
    Ubicacion ubicacion
) {

    public static GetIncidenciaDetailsDto of (Incidencia incidencia) {

        return new GetIncidenciaDetailsDto(
                incidencia.getFecha(),
                incidencia.getTitulo(),
                incidencia.getDescripcion(),
                incidencia.getEstado().toString(),
                incidencia.getUrgencia(),
                incidencia.getCategoria().getNombre(),
                incidencia.getListaNotas().stream()
                        .map(GetNotaDto::of)
                        .toList(),
                incidencia.getEquipo(),
                incidencia.getUbicacion()
        );
    }
}
