package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Categoria;
import com.salesianostriana.dam.satapp.model.Equipo;

import java.time.LocalDate;
import java.util.List;

public record GetIncidenciaDto(
        Long id,
        LocalDate fecha,
        String titulo,
        String descripcion,
        String estado,
        int urgencia,
        GetCategoriaDto categoria,
        List<GetNotaDto> notas,
        Equipo equipo

) {
}
