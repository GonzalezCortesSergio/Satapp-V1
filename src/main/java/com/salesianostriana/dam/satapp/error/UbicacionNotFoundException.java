package com.salesianostriana.dam.satapp.error;

public class UbicacionNotFoundException extends RuntimeException {
    public UbicacionNotFoundException(String message) {
        super(message);
    }

    public UbicacionNotFoundException() {

        super("No se han encontrado ubicaciones");
    }
}
