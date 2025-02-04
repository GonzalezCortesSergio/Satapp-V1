package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.dto.CreateEquipoDto;
import com.salesianostriana.dam.satapp.error.EquipoNotFoundException;
import com.salesianostriana.dam.satapp.error.PasPermisoDenegadoException;
import com.salesianostriana.dam.satapp.error.UbicacionNotFoundException;
import com.salesianostriana.dam.satapp.model.Equipo;
import com.salesianostriana.dam.satapp.model.Ubicacion;
import com.salesianostriana.dam.satapp.repository.EquipoRepository;
import com.salesianostriana.dam.satapp.repository.UbicacionRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UbicacionRepository ubicacionRepository;


    public List<Equipo> findAllWithNoIncidencia() {

        List<Equipo> result = equipoRepository.findAllWithNoIncidencia();

        if(result.isEmpty())
            throw new EquipoNotFoundException();

        return result;
    }

    @Transactional
    public Equipo save(Long idAdmin, CreateEquipoDto equipoDto) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));


        return equipoRepository.save(equipoDto.toEquipo());
    }

    @Transactional
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

    @Transactional
    public void remove(Long idAdmin, Long idEquipo) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(idAdmin));


        equipoRepository.deleteById(idEquipo);
    }

    @Transactional
    public Equipo cambiarUbicacion(Long idAdmin, Long idEquipo, String nombreUbicacion) {

        if(usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException(idAdmin);


        return equipoRepository.findById(idEquipo)
                .map(equipo -> {

                    equipo.setUbicacion(ubicacionRepository.findByNombre(nombreUbicacion)
                            .orElseThrow(UbicacionNotFoundException::new));

                    return equipoRepository.save(equipo);
                }).orElseThrow(() -> new EquipoNotFoundException(idEquipo));

    }
}
