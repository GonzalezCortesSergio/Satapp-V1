package com.salesianostriana.dam.satapp.error;

public class TecnicoNoRelacionadoException extends RuntimeException {
    public TecnicoNoRelacionadoException(String message) {
        super(message);
    }

    public TecnicoNoRelacionadoException(Long idTecnico, Long idIncidencia){

      super("El técnico con ID: %d no está gestionando la incidencia con ID: %d".formatted(idTecnico, idIncidencia));
    }
}
