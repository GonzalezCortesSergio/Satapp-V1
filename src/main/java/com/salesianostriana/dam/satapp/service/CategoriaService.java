package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.error.PasPermisoDenegadoException;
import com.salesianostriana.dam.satapp.model.Categoria;
import com.salesianostriana.dam.satapp.repository.IncidenciaRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;

    public Categoria crearCategoria(Long id){

        if (usuarioRepository.findByIdPas(id).isEmpty()){
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(id));
        }



    }


}
