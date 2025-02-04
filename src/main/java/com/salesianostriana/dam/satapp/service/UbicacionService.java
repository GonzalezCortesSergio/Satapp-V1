package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.error.NombreRepetidoException;
import com.salesianostriana.dam.satapp.error.PasPermisoDenegadoException;
import com.salesianostriana.dam.satapp.model.Ubicacion;
import com.salesianostriana.dam.satapp.repository.UbicacionRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final UsuarioRepository usuarioRepository;


    public Ubicacion save(Long idAdmin, String nombreUbicacion) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));

        Optional<Ubicacion> optionalUbicacion = ubicacionRepository.findByNombre(nombreUbicacion);
        if(optionalUbicacion.isPresent())
            throw new NombreRepetidoException(optionalUbicacion.get());

        Ubicacion ubicacion = Ubicacion.builder()
                .nombre(nombreUbicacion)
                .build();

        return ubicacionRepository.save(ubicacion);
    }
}
