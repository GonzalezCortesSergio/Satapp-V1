package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.error.CategoriaNotFoundException;
import com.salesianostriana.dam.satapp.error.NombreRepetidoException;
import com.salesianostriana.dam.satapp.error.PasPermisoDenegadoException;
import com.salesianostriana.dam.satapp.model.Categoria;
import com.salesianostriana.dam.satapp.repository.CategoriaRepository;
import com.salesianostriana.dam.satapp.repository.IncidenciaRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public Categoria crearCategoria(Long id, String nombre){

        if (usuarioRepository.findByIdPas(id).isEmpty()){
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(id));
        }

        Optional<Categoria> categoriaOptional = categoriaRepository.findByNombre(nombre);

        if (categoriaOptional.isPresent()){
            throw new NombreRepetidoException(categoriaOptional.get());
        }

        Categoria categoria = Categoria.builder()
                .nombre(nombre)
                .build();

        return categoriaRepository.save(categoria);

    }

    public Categoria crearCategoriaHija(Long id, String nombre, String nombreHija){

        if (usuarioRepository.findByIdPas(id).isEmpty()){
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(id));
        }

        Categoria categoriaPadre = categoriaRepository.findByNombre(nombre)
                .orElseThrow(CategoriaNotFoundException::new);

        Optional<Categoria> categoriaOptional = categoriaRepository.findByNombre(nombreHija);

        if (categoriaOptional.isPresent()){
            throw new NombreRepetidoException(categoriaOptional.get());
        }

        Categoria categoria = Categoria.builder()
                .categoriaPadre(categoriaPadre)
                .nombre(nombreHija)
                .build();

        return categoriaRepository.save(categoria);
    }

}
