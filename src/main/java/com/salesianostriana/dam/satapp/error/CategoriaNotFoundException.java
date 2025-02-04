package com.salesianostriana.dam.satapp.error;

public class CategoriaNotFoundException extends RuntimeException {

    public CategoriaNotFoundException(){
        super("No se ha encontrado ninguna categoría");
    }

    public CategoriaNotFoundException(String nombre){

        super("No se ha encontrado ninguna categoría con ese nombre");
    }
}
