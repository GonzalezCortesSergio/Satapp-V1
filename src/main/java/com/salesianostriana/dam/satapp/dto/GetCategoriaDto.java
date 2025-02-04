package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Categoria;

public record GetCategoriaDto(
        String nombre,
        Categoria categoriaPadre
) {
    public static GetCategoriaDto of(Categoria categoria){
        return new GetCategoriaDto(
                categoria.getNombre(),
                categoria.getCategoriaPadre()
        );
    }
}
