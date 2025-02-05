package com.salesianostriana.dam.satapp.service;

import com.salesianostriana.dam.satapp.dto.CreateIncidenciaDto;
import com.salesianostriana.dam.satapp.dto.CreateNotaDto;
import com.salesianostriana.dam.satapp.dto.EditIncidenciaDto;
import com.salesianostriana.dam.satapp.error.*;
import com.salesianostriana.dam.satapp.model.Estado;
import com.salesianostriana.dam.satapp.model.Incidencia;
import com.salesianostriana.dam.satapp.model.Nota;
import com.salesianostriana.dam.satapp.repository.*;
import com.salesianostriana.dam.satapp.error.*;
import com.salesianostriana.dam.satapp.model.*;
import com.salesianostriana.dam.satapp.repository.IncidenciaRepository;
import com.salesianostriana.dam.satapp.repository.IncidenciaTecnicoRepository;
import com.salesianostriana.dam.satapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final UbicacionRepository ubicacionRepository;
    private final EquipoRepository equipoRepository;
    private final IncidenciaTecnicoRepository incidenciaTecnicoRepository;

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

    @Transactional
    public Incidencia edit(Long idIncidencia, Long idUsuario, EditIncidenciaDto editIncidenciaDto) {

        Incidencia antigua = incidenciaRepository.findByUsuarioAndIdAbiertaOrPendiente(idUsuario, idIncidencia)
                        .orElseThrow(
                                () -> new IncidenciaNotFoundException("No se ha encontrado una incidencia ABIERTA o PENDIENTE con el ID: %d para el usuario con ID: %d".formatted(idIncidencia, idUsuario))
                        );

        antigua.setDescripcion(editIncidenciaDto.descripcion());

        return antigua;
    }

    @Transactional
    public void deleteById(Long idUsuario, Long idIncidencia) {

        Incidencia incidencia = findByIdAndUsuario(idUsuario, idIncidencia);

        if(!incidencia.getEstado().toString().equals("ABIERTA"))
            throw new IncidenciaNotAbiertaException();

        incidencia.setUsuario(null);
        incidencia.setUbicacion(null);

        incidenciaRepository.deleteById(idIncidencia);
    }

    @Transactional
    public Incidencia addNota(Long idUsuario, Long idIncidencia, CreateNotaDto notaDto) {

        return incidenciaRepository.findByUsuarioAndIdNotCerrada(idUsuario, idIncidencia)
                .map(incidencia -> {


                    incidencia.addNota(
                            Nota.builder()
                                    .fecha(LocalDate.now())
                                    .contenido(notaDto.contenido())
                                    .autor(incidencia.getUsuario().getNombre())
                                    .build()
                    );

                    return incidenciaRepository.save(incidencia);
                }).orElseThrow(() -> new IncidenciaNotFoundException(idUsuario, idIncidencia));
    }

    @Transactional
    public Incidencia eliminarNota(Long idUsuario, Long idIncidencia, Long idNota) {

        return incidenciaRepository.findByUsuarioAndIdNotCerrada(idUsuario, idIncidencia)
                .map(incidencia -> {
                   incidencia.getListaNotas().stream()
                           .filter(nota -> nota.getId().equals(idNota))
                           .findFirst()
                           .ifPresent(nota -> {
                               incidencia.getListaNotas().remove(nota);
                           });
                   return incidenciaRepository.save(incidencia);
                }).orElseThrow(() -> new IncidenciaNotFoundException(idUsuario, idIncidencia));
    }


    public List<Incidencia> findAll(Long idAdmin, String filtro, boolean ordenarFecha) {

        if (usuarioRepository.findByIdPas(idAdmin).isEmpty())
            throw new PasPermisoDenegadoException(idAdmin);

        String[] tipoFiltro = filtro.split("-");
        List<Incidencia> result;
        if (tipoFiltro.length > 1) {

            switch (tipoFiltro[0]) {
                case "categoria":
                    result = incidenciaRepository.findAllByCategoriaNombre(tipoFiltro[1]);

                    if (result.isEmpty())
                        throw new IncidenciaNotFoundException();

                    return result;

                case "estado":

                    result = incidenciaRepository.findAllByEstado(tipoFiltro[1].toUpperCase());

                    if (result.isEmpty())
                        throw new IncidenciaNotFoundException();

                    return result;

                case "ubicacion":

                    result = incidenciaRepository.findAllByUbicacion(tipoFiltro[1]);

                    if (result.isEmpty())
                        throw new IncidenciaNotFoundException();

                    return result;

                default:

                    if (ordenarFecha)
                        result = incidenciaRepository.findAllOrderByFecha();

                    else
                        result = incidenciaRepository.findAll();

                    if (result.isEmpty())
                        throw new IncidenciaNotFoundException();

                    return result;
            }

        }

        if (ordenarFecha)
            result = incidenciaRepository.findAllOrderByFecha();

        else
            result = incidenciaRepository.findAll();

        if (result.isEmpty())
            throw new IncidenciaNotFoundException();

        return result;


    }

    public List<Incidencia> findAllTecnico(String nombreCategoria, Long idTecnico){

        if (usuarioRepository.findByIdTecnico(idTecnico).isEmpty()){
            throw new TecnicoPermisoDenegadoException();
        }
        List<Incidencia> result;

        if(nombreCategoria.equalsIgnoreCase("no"))
            result = incidenciaRepository.findAllEstadoNoCerrada();

        else
            result = incidenciaRepository.findAllEstadoNoCerradaFiltroCategoria(nombreCategoria);

        if(result.isEmpty())
            throw new IncidenciaNotFoundException();

        return result;

    }

    @Transactional
    public Incidencia save(Long idUsuario, CreateIncidenciaDto incidenciaDto, String categoria, Long idEquipo, String ubicacion) {

        Incidencia incidencia = incidenciaDto.toIncidencia();

        incidencia.setUsuario(usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNotFoundException(idUsuario)));
        incidencia.setCategoria(categoriaRepository.findByNombre(categoria).orElseThrow(() -> new CategoriaNotFoundException(categoria)));
        incidencia.setUbicacion(ubicacionRepository.findByNombre(ubicacion).orElseThrow(UbicacionNotFoundException::new));
        incidencia.setEquipo(equipoRepository.findById(idEquipo).orElse(null));


        incidencia.setFecha(LocalDate.now());
        incidencia.setEstado(Estado.ABIERTA);

        return incidenciaRepository.save(incidencia);

    }

    @Transactional
    public Incidencia seleccionarIncidencia(Long idTecnico, Long idIncidencia) {

        Tecnico tecnico = usuarioRepository.findByIdTecnico(idTecnico)
                .orElseThrow(() -> new TecnicoPermisoDenegadoException());

        if (incidenciaTecnicoRepository.findByIdIncidenciaAndIdTecnico(idIncidencia, idTecnico).isPresent())
            throw new IncidenciaTecnicoExistsException(idTecnico, idIncidencia);

        Incidencia incidencia = incidenciaRepository.findById(idIncidencia)
                .orElseThrow(() -> new IncidenciaNotFoundException(idIncidencia));


        IncidenciaTecnico it = new IncidenciaTecnico();

        it.addToIncidencia(incidencia);
        it.addToTecnico(tecnico);

        if(incidenciaTecnicoRepository.findByIdIncidenciaTecnicoResponsable(idIncidencia).isEmpty())
            it.setTecnicoResponsable(true);


        incidenciaTecnicoRepository.save(it);

        return incidencia;
    }

    public List<Incidencia> findAllByTecnico(Long idTecnico) {

        List<Incidencia> result = incidenciaRepository.findAllByTecnicoGestiona(idTecnico);

        if(result.isEmpty())
            throw new IncidenciaNotFoundException();

        return result;
    }

}
