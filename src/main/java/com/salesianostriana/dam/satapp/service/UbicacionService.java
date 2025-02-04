package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.error.NombreRepetidoException;
import com.salesianostriana.dam.satapp.error.PasPermisoDenegadoException;
import com.salesianostriana.dam.satapp.error.UbicacionNotFoundException;
import com.salesianostriana.dam.satapp.model.Ubicacion;
import com.salesianostriana.dam.satapp.repository.UbicacionRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final UsuarioRepository usuarioRepository;


    public List<Ubicacion> findAll() {

        List<Ubicacion> result = ubicacionRepository.findAll();

        if(result.isEmpty())
            throw new UbicacionNotFoundException();

        return ubicacionRepository.findAll();
    }

    public Ubicacion save(Long idAdmin, String nombreUbicacion) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));

        Optional<Ubicacion> ubicacionOptional = ubicacionRepository.findByNombre(nombreUbicacion);

        if(ubicacionOptional.isPresent())
            throw new NombreRepetidoException(ubicacionOptional.get());

        Ubicacion ubicacion = Ubicacion.builder()
                .nombre(nombreUbicacion)
                .build();

        return ubicacionRepository.save(ubicacion);
    }

    public void deleteByNombre(Long idAdmin, String nombreUbicacion) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException(idAdmin);

        ubicacionRepository.deleteByNombre(nombreUbicacion);
    }
}
