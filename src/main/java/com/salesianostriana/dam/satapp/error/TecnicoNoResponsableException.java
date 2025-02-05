package com.salesianostriana.dam.satapp.error;

public class TecnicoNoResponsableException extends RuntimeException {
    public TecnicoNoResponsableException(String message) {
        super(message);
    }

    public TecnicoNoResponsableException(Long idTecnico, Long idIncidencia) {
        super("El técnico de ID: %d no es responsable de la incidencia con ID: %d".formatted(idTecnico, idIncidencia));
    }
}
