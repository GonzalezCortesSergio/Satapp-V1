package com.salesianostriana.dam.satapp.error;

public class IncidenciaNotFoundException extends RuntimeException {
    public IncidenciaNotFoundException(String message) {
        super(message);
    }

    public IncidenciaNotFoundException(Long idUsuario) {
        super("No se han encontrado incidencias para el usuario con ID: %d".formatted(idUsuario));
    }

    public IncidenciaNotFoundException(Long idUsuario, Long idIncidencia) {

        super("No se ha encontrado una incidencia con el ID: %d para el usuario con ID: %d".formatted(idIncidencia, idUsuario));
    }

    public IncidenciaNotFoundException(){
        super("No se han encontrado incidencias");
    }

}
