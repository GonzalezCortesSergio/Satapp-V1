package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.*;

import java.time.LocalDate;
import java.util.List;

public record GetIncidenciaDetailsAdminDto(
        LocalDate fecha,
        String titulo,
        String descripcion,
        String estado,
        int urgencia,
        String categoria,
        List<GetNotaDto> notas,
        Equipo equipo,
        Ubicacion ubicacion,
        List<GetUsuarioDto> tecnicosGestionan
) {

    public static GetIncidenciaDetailsAdminDto of (Incidencia incidencia) {

        return new GetIncidenciaDetailsAdminDto(
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
                incidencia.getUbicacion(),
                incidencia.getTecnicosGestionan().stream()
                        .map(IncidenciaTecnico::getTecnico)
                        .map(GetUsuarioDto::of)
                        .toList()
        );
    }
}
