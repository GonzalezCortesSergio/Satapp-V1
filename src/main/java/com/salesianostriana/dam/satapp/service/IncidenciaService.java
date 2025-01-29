package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.error.IncidenciaNotFoundException;
import com.salesianostriana.dam.satapp.error.UsuarioNotFoundException;
import com.salesianostriana.dam.satapp.model.Estado;
import com.salesianostriana.dam.satapp.model.Incidencia;
import com.salesianostriana.dam.satapp.model.Usuario;
import com.salesianostriana.dam.satapp.repository.IncidenciaRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;


    public List<Incidencia> findByUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(
                        () -> new UsuarioNotFoundException("No se ha encontrado un usuario con el ID: %d".formatted(idUsuario))
                );

        List<Incidencia> result = incidenciaRepository.findByUsuario(usuario, Estado.CERRADA);

        if(result.isEmpty())
            throw new IncidenciaNotFoundException("No se han encontrado incidencias");

        return result;
    }
}
