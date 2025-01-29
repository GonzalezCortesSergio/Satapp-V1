package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.error.IncidenciaNotFoundException;
import com.salesianostriana.dam.satapp.model.Estado;
import com.salesianostriana.dam.satapp.model.Incidencia;
import com.salesianostriana.dam.satapp.repository.IncidenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    public List<Incidencia> findAllByUsuario(Long idUsuario) {


        List<Incidencia> result = incidenciaRepository.findAllByUsuario(idUsuario, Estado.CERRADA);

        if(result.isEmpty())
            throw new IncidenciaNotFoundException("No se han encontrado incidencias para el usuario con ID: %d".formatted(idUsuario));

        return result;
    }

    public Incidencia findByIdAndUsuario(Long idUsuario, Long idIncidencia) {

        return incidenciaRepository.findByUsuarioAndIdFetch(idUsuario, idIncidencia)
                .orElseThrow(
                        () -> new IncidenciaNotFoundException("No se ha encontrado una incidencia con el ID: %d para el usuario con ID: %d".formatted(idIncidencia, idUsuario))
                );
    }
}
