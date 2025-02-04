package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.Categoria;

public record GetCategoriaDto(
        String nombre,
        String categoriaPadre
) {
    public static GetCategoriaDto of(Categoria categoria){
        if(categoria.getCategoriaPadre() == null ) {

            return new GetCategoriaDto(
                    categoria.getNombre(),
                    " "
            );
        }

        return new GetCategoriaDto(
                categoria.getNombre(),
                categoria.getCategoriaPadre().getNombre()
        );
    }
}
