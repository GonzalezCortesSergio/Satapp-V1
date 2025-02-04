package com.salesianostriana.dam.satapp.error;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String message) {
        super(message);
    }

    public CategoriaNotFoundException(){
        super("No se ha encontrado ninguna categoría con ese nombre");
    }
}
