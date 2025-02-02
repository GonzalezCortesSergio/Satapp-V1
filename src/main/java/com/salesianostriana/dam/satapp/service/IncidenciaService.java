package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.dto.EditIncidenciaDto;
import com.salesianostriana.dam.satapp.error.IncidenciaNotAbiertaException;
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
            throw new IncidenciaNotFoundException(idUsuario);

        return result;
    }

    public Incidencia findByIdAndUsuario(Long idUsuario, Long idIncidencia) {

        return incidenciaRepository.findByUsuarioAndIdFetch(idUsuario, idIncidencia)
                .orElseThrow(
                        () -> new IncidenciaNotFoundException(idUsuario, idIncidencia)
                );
    }

    public Incidencia edit(Long idIncidencia, Long idUsuario, EditIncidenciaDto editIncidenciaDto) {

        Incidencia antigua = incidenciaRepository.findByUsuarioAndIdAbiertaOrPendiente(idUsuario, idIncidencia)
                        .orElseThrow(
                                () -> new IncidenciaNotFoundException("No se ha encontrado una incidencia ABIERTA o PENDIENTE con el ID: %d para el usuario con ID: %d".formatted(idIncidencia, idUsuario))
                        );

        antigua.setDescripcion(editIncidenciaDto.descripcion());

        return antigua;
    }

    public void deleteById(Long idUsuario, Long idIncidencia) {

        Incidencia incidencia = findByIdAndUsuario(idUsuario, idIncidencia);

        if(!incidencia.getEstado().toString().equals("ABIERTA"))
            throw new IncidenciaNotAbiertaException();

        incidencia.setUsuario(null);
        incidencia.setUbicacion(null);

        incidenciaRepository.deleteById(idIncidencia);
    }
}
