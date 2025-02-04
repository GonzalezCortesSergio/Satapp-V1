package com.salesianostriana.dam.satapp.error;

public class PasPermisoDenegadoException extends RuntimeException {
    public PasPermisoDenegadoException(String message) {
        super(message);
    }

    public PasPermisoDenegadoException(Long idAdmin) {

        super("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));
    }
}
