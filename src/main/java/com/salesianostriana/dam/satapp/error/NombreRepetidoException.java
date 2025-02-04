package com.salesianostriana.dam.satapp.error;

import com.salesianostriana.dam.satapp.model.Categoria;
import com.salesianostriana.dam.satapp.model.Ubicacion;

public class NombreRepetidoException extends RuntimeException{

    public NombreRepetidoException(Ubicacion ubicacion) {

        super("Esta ubicación ya existe");
    }

    public NombreRepetidoException(Categoria categoria){
        super("Esta categoría ya existe");
    }
}
