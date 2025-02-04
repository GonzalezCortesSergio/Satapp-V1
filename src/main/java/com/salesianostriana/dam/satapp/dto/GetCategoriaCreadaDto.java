package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Categoria;

public record GetCategoriaCreadaDto(
        String nombre
) {
    public static GetCategoriaCreadaDto of(Categoria categoria){
        return new GetCategoriaCreadaDto(
                categoria.getNombre()
        );
    }
}
