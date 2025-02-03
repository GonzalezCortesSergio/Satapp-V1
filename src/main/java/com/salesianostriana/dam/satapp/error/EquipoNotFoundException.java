package com.salesianostriana.dam.satapp.error;

public class EquipoNotFoundException extends RuntimeException {
    public EquipoNotFoundException(String message) {
        super(message);
    }

    public EquipoNotFoundException() {
      super("No se han encontrado equipos");
    }

    public EquipoNotFoundException(Long idEquipo) {
        super("No se ha encontrado un equipo con el ID: %d".formatted(idEquipo));
    }
}
