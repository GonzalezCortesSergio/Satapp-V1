package com.salesianostriana.dam.satapp.error;

public class IncidenciaTecnicoExistsException extends RuntimeException {
    public IncidenciaTecnicoExistsException(String message) {
        super(message);
    }

    public IncidenciaTecnicoExistsException(Long idTecnico, Long idIncidencia) {
      super("El técnico con ID: %d ya está gestionando la incidencia con ID: %d".formatted(idTecnico, idIncidencia));
    }
}
