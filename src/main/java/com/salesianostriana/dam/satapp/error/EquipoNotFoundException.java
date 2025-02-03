package com.salesianostriana.dam.satapp.error;

public class EquipoNotFoundException extends RuntimeException {
    public EquipoNotFoundException(String message) {
        super(message);
    }

    public EquipoNotFoundException() {
      super("No se han encontrado equipos");
    }
}
