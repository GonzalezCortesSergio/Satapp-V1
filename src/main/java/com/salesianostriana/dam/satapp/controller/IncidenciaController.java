package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                            schema = @Schema(implementation = GetIncidenciaListDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "count": 1,
                                                                            "results": [
                                                                                {
                                                                                    "id": 1,
                                                                                    "titulo": "Ordenador ardiendo",
                                                                                    "descripcion": "No sé, el ordenador está ardiendo socorro ayuda ya porfavor",
                                                                                    "urgencia": 5
                                                                                }
                                                                            ]
                                                                        }
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
    public GetIncidenciaListDto findIncidenciasByUsuario(@PathVariable Long idUsuario) {

        return GetIncidenciaListDto.of(incidenciaService.findAllByUsuario(idUsuario));
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

    @PutMapping("/usuario/{usuarioId}/editar/{idIncidencia}")
    @Operation(summary = "Un usuario edita la descripción de una incidencia que esté ABIERTA o PENDIENTE")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ha editado la descripción correctamente",

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
                                                                            "descripcion": "Estaba fumando y tiré una colilla al ordenador pensando que no ardería y salió ardiendo, socorro",
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
                            description = "No se ha encontrado la incidencia, por lo que no se ha podido editar",
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
                                                                            "detail": "No se ha encontrado una incidencia ABIERTA o PENDIENTE con el ID: 2 para el usuario con ID: 1",
                                                                            "instance": "/api/incidencia/usuario/1/editar/2"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public GetIncidenciaDetailsDto editIncidencia(@PathVariable Long usuarioId, @PathVariable Long idIncidencia,
                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                          description = "Descripción a cambiar",
                                                          required = true,
                                                          content = {
                                                                  @Content(
                                                                          mediaType = "application/json",
                                                                          schema = @Schema(implementation = EditIncidenciaDto.class),
                                                                          examples = {
                                                                                  @ExampleObject(
                                                                                          value = """
                                                                                                    {
                                                                                                        "descripcion": "Estaba fumando y tiré una colilla al ordenador pensando que no ardería y salió ardiendo, socorro"
                                                                                                    }
                                                                                                  """
                                                                                  )
                                                                          }
                                                                  )
                                                          }
                                                  )
                                                  @RequestBody EditIncidenciaDto incidenciaDto) {

        return GetIncidenciaDetailsDto.of(incidenciaService.edit(idIncidencia, usuarioId, incidenciaDto));
    }

    @DeleteMapping("/usuario/{idUsuario}/borrar/{idIncidencia}")
    @Operation(summary = "Se borra la incidencia de un usuario que se encuentra abierta")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Se ha borrado la incidencia correctamente",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado una incidencia",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                                {
                                                                    "type": "about:blank",
                                                                    "title": "Incidencia no encontrada",
                                                                    "status": 404,
                                                                    "detail": "No se ha encontrado una incidencia con el ID: 2 para el usuario con ID: 1",
                                                                    "instance": "/api/incidencia/usuario/1/borrar/2"
                                                                }
                                                            """
                                            )
                                    }
                            )

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "La incidencia a borrar no está abierta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                                {
                                                                    "type": "about:blank",
                                                                    "title": "Incidencia no abierta",
                                                                    "status": 400,
                                                                    "detail": "La incidencia que intentas borrar no está abierta",
                                                                    "instance": "/api/incidencia/usuario/1/borrar/1"
                                                                }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> deleteByIdIncidenciaAbierta(@PathVariable Long idUsuario, @PathVariable Long idIncidencia) {

        incidenciaService.deleteById(idUsuario, idIncidencia);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idIncidencia}/usuario/{idUsuario}/addNota")
    @Operation(summary = "Se añade una nota a una incidencia que no está cerrada")
    @ApiResponses(
           value = {
                   @ApiResponse(
                           responseCode = "200",
                           description = "Se añade la nota correctamente",
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
                                                                        "descripcion": "No sÃ©, el ordenador estÃ¡ ardiendo socorro ayuda ya porfavor",
                                                                        "estado": "ABIERTA",
                                                                        "urgencia": 5,
                                                                        "categoria": "Ordenadores",
                                                                        "notas": [
                                                                            {
                                                                                "id": 1,
                                                                                "fecha": "2025-02-04",
                                                                                "contenido": "Ha dejado de arder, pero está todo chamuscao",
                                                                                "autor": "Pablo"
                                                                            }
                                                                        ],
                                                                        "equipo": {
                                                                            "id": 1,
                                                                            "nombre": "Ordenador",
                                                                            "caracteristicas": "Un ordenador to wapo",
                                                                            "ubicacion": null,
                                                                            "deleted": false
                                                                        },
                                                                        "ubicacion": {
                                                                            "id": 1,
                                                                            "nombre": "Aula 1",
                                                                            "deleted": false
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
                           description = "No se encuentra una incidencia para agregar una nota",
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
                                                                        "detail": "No se ha encontrado una incidencia con el ID: 2 para el usuario con ID: 1",
                                                                        "instance": "/api/incidencia/2/usuario/1/addNota"
                                                                    }
                                                                   """
                                                   )
                                           }
                                   )
                           }
                   )
           }
    )
    public GetIncidenciaDetailsDto addNota(@PathVariable Long idIncidencia, @PathVariable Long idUsuario, @RequestBody CreateNotaDto notaDto) {

        return GetIncidenciaDetailsDto.of(incidenciaService.addNota(idUsuario, idIncidencia, notaDto));
    }


}
