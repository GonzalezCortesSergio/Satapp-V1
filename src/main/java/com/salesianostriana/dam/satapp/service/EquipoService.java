package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.dto.CreateEquipoDto;
import com.salesianostriana.dam.satapp.model.Equipo;
import com.salesianostriana.dam.satapp.repository.EquipoRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;

    public Equipo save(Long idAdmin, CreateEquipoDto equipoDto) {

    }
}
