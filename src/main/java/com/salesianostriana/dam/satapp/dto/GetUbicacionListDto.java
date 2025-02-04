package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Ubicacion;

import java.util.List;

public record GetUbicacionListDto(
        Long count,
        List<String> results
) {

    public static GetUbicacionListDto of (List<Ubicacion> ubicacionList) {

        return new GetUbicacionListDto(
                (long) ubicacionList.size(),
                ubicacionList.stream()
                        .map(Ubicacion::getNombre)
                        .toList()
        );
    }
}
