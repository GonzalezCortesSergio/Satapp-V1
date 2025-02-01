package com.salesianostriana.dam.satapp.error;

public class IncidenciaNotAbiertaException extends RuntimeException {
    public IncidenciaNotAbiertaException(String message) {
        super(message);
    }

    public IncidenciaNotAbiertaException() {

        super("La incidencia que intentas borrar no está abierta");
    }
}
