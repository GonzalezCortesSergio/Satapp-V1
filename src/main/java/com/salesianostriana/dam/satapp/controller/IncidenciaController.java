package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.GetIncidenciaDetailsDto;
import com.salesianostriana.dam.satapp.dto.GetIncidenciaDto;
import com.salesianostriana.dam.satapp.service.IncidenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/incidencia")
@RequiredArgsConstructor
@Tag(name = "Incidencia",
        description = "Controlador de incidencias, para poder realizar todas sus operaciones de gestión")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;


    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Se buscan todas las incidencias abiertas por un usuario que no estén en estado CERRADA")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se han encontrado las incidencias correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = GetIncidenciaDto.class)),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        [
                                                                            {
                                                                                "id": 1,
                                                                                "titulo": "Ordenador ardiendo",
                                                                                "descripcion": "No sé, el ordenador está ardiendo socorro ayuda ya porfavor",
                                                                                "urgencia": 5
                                                                            }
                                                                        ]
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se han encontrado incidencias",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Incidencia no encontrada",
                                                                            "status": 404,
                                                                            "detail": "No se han encontrado incidencias para el usuario con ID: 3",
                                                                            "instance": "/api/incidencia/usuario/3"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public List<GetIncidenciaDto> findIncidenciasByUsuario(@PathVariable Long idUsuario) {

        return incidenciaService.findAllByUsuario(idUsuario).stream()
                .map(GetIncidenciaDto::of)
                .toList();
    }

    @GetMapping("/usuario/{idUsuario}/detalles/{idIncidencia}")
    @Operation(summary = "Se muestran los detalles de una incidencia seleccionada por el usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se muestran los datos de la incidencia correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetIncidenciaDetailsDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "fecha": "2025-01-28",
                                                                            "titulo": "Ordenador ardiendo",
                                                                            "descripcion": "No sé, el ordenador está ardiendo, socorro ayuda ya porfavor",
                                                                            "estado": "ABIERTA",
                                                                            "urgencia": 5,
                                                                            "categoria": "Ordenadores",
                                                                            "notas": [],
                                                                            "equipo": null,
                                                                            "ubicacion": {
                                                                                "id": 1,
                                                                                "nombre": "Aula 1"
                                                                            }
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado la incidencia",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Incidencia no encontrada",
                                                                            "status": 404,
                                                                            "detail": "No se ha encontrado una incidencia con el ID: 1 para el usuario con ID: 2",
                                                                            "instance": "/api/incidencia/usuario/2/detalles/1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public GetIncidenciaDetailsDto findIncidenciaByUsuarioAndId(@PathVariable Long idUsuario, @PathVariable Long idIncidencia) {

        return GetIncidenciaDetailsDto.of(incidenciaService.findByIdAndUsuario(idUsuario, idIncidencia));
    }

}
