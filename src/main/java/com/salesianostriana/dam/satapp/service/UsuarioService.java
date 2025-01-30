package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.dto.CreateUsuarioDto;
import com.salesianostriana.dam.satapp.error.PasPermisoDenegadoException;
import com.salesianostriana.dam.satapp.error.TipoUsusarioNoPermitidoException;
import com.salesianostriana.dam.satapp.error.UsuarioNotFoundException;
import com.salesianostriana.dam.satapp.model.Personal;
import com.salesianostriana.dam.satapp.model.Tipo;
import com.salesianostriana.dam.satapp.model.Usuario;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Long id, CreateUsuarioDto createUsuarioDto, String tipoUsuario, String tipoPersonal){

        if (usuarioRepository.findByIdPas(id).isEmpty()){
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(id));
        }


        if(tipoUsuario.equalsIgnoreCase("Alumno")){
            return usuarioRepository.save(createUsuarioDto.toAlumno());
        } else if (tipoUsuario.equalsIgnoreCase("Tecnico")) {
            return usuarioRepository.save(createUsuarioDto.toTecnico());
        }

        if (!tipoUsuario.equalsIgnoreCase("Personal")){
            throw new TipoUsusarioNoPermitidoException("No se puede crear ese tipo");
        }


        Personal personal = createUsuarioDto.toPersonal();

        personal.setTipo(Tipo.valueOf(tipoPersonal));

        return usuarioRepository.save(personal);

    }

    public Usuario editarUsuario(Long idAdmin, CreateUsuarioDto createUsuarioDto, Long id){

        if (usuarioRepository.findByIdPas(idAdmin).isEmpty()){
            throw new PasPermisoDenegadoException("No se ha encontrado un usuario PAS con el id: %d".formatted(id));
        }

        return usuarioRepository.findById(id)
                .map(old -> {
                    old.setNombre(createUsuarioDto.nombre());
                    old.setUsername(createUsuarioDto.username());
                    old.setPassword(createUsuarioDto.password());
                    old.setEmail(createUsuarioDto.email());
                    old.setRole(createUsuarioDto.role());
                    return usuarioRepository.save(old);
                })
                .orElseThrow(() -> new UsuarioNotFoundException("No se ha encontrado ningun usuario con la id:  %d".formatted(id)));

    }


}
