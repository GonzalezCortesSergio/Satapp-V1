package com.salesianostriana.dam.satapp.error;

public class NombreRepetidoException extends RuntimeException{

    public NombreRepetidoException() {

        super("Esta ubicación ya existe");
    }
}
