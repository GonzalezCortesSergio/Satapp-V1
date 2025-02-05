package com.salesianostriana.dam.satapp.error;

public class TecnicoPermisoDenegadoException extends RuntimeException {
    public TecnicoPermisoDenegadoException(String message) {
        super(message);
    }

    public TecnicoPermisoDenegadoException(){
        super("El usuario debe de ser un técnico para acceder");
    }
}
