package com.salesianostriana.dam.satapp.error;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String message) {
        super(message);
    }

    public UsuarioNotFoundException(Long idUsuario) {

        super("No se ha encontrado un usuario con el ID: %d".formatted(idUsuario));
    }
}
