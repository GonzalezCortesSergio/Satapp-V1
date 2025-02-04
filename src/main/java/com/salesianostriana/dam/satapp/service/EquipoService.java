package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.dto.CreateEquipoDto;
import com.salesianostriana.dam.satapp.error.EquipoNotFoundException;
import com.salesianostriana.dam.satapp.error.PasPermisoDenegadoException;
import com.salesianostriana.dam.satapp.model.Equipo;
import com.salesianostriana.dam.satapp.repository.EquipoRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;


    public List<Equipo> findAllWithNoIncidencia() {

        List<Equipo> result = equipoRepository.findAllWithNoIncidencia();

        if(result.isEmpty())
            throw new EquipoNotFoundException();

        return result;
    }

    public Equipo save(Long idAdmin, CreateEquipoDto equipoDto) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));


        return equipoRepository.save(equipoDto.toEquipo());
    }

    public Equipo edit(Long idAdmin, Long idEquipo, CreateEquipoDto equipoDto) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));

        return equipoRepository.findById(idEquipo)
                .map(antiguo -> {

                    antiguo.setNombre(equipoDto.nombre());
                    antiguo.setCaracteristicas(equipoDto.caracteristicas());
                    return equipoRepository.save(antiguo);
                })
                .orElseThrow(() -> new EquipoNotFoundException(idEquipo));
    }

    public void remove(Long idAdmin, Long idEquipo) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));


        equipoRepository.deleteById(idEquipo);
    }
}
