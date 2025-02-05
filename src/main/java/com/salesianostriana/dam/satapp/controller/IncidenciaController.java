package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.*;
import com.salesianostriana.dam.satapp.service.IncidenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/incidencia")
@RequiredArgsConstructor
@Tag(name = "Incidencia",
        description = "Controlador de incidencias, para poder realizar todas sus operaciones de gestión")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    
    @Operation(summary = "Un técnico puede ver la lista de incidencias no cerradas y las puede " +
            "filtar por categoría",
            description = "El método tiene un parámetro de petición llamado nombreCategoria. Si se quiere filtrar " +
                    "por alguna de las categorías se debera indicar")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "No hay ninguna incidencia",
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
                                                                         "detail": "No se han encontrado incidencias",
                                                                         "instance": "/api/incidencia/tecnico/2/categoria"
                                                                    }
                                                                   """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para ver las incidencias",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    {
                                                                         "type": "about:blank",
                                                                         "title": "Tecnico permiso no concedido",
                                                                         "status": 401,
                                                                         "detail": "El usuario debe de ser un técnico para acceder",
                                                                         "instance": "/api/incidencia/tecnico/1/categoria"
                                                                     }
                                                                   """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "El técnico puede acceder a todas las incidencias filtradas",
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
                            responseCode = "200",
                            description = "El técnico puede acceder a todas las incidencias",
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
                    )
            }
    )
    @GetMapping("/tecnico/{idTecnico}/categoria")
    public GetIncidenciaListDto findIncidenciasNoCerradas(
            @Parameter(
                    description = "valor para filtrar por categoria",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false, defaultValue = "no") String nombreCategoria, @PathVariable Long idTecnico){
        return GetIncidenciaListDto.of(incidenciaService.findAllTecnico(nombreCategoria, idTecnico));
    }


    @Operation(summary = "Se ven las incidencias que gestiona un técnico")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "El técnico ve las incidencias que gestiona correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetIncidenciaListDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "count": 2,
                                                                            "results": [
                                                                                {
                                                                                    "id": 1,
                                                                                    "titulo": "Ordenador ardiendo",
                                                                                    "descripcion": "No sÃ©, el ordenador estÃ¡ ardiendo socorro ayuda ya porfavor",
                                                                                    "urgencia": 5
                                                                                },
                                                                                {
                                                                                    "id": 2,
                                                                                    "titulo": "Boquete pared",
                                                                                    "descripcion": "Un nota ha hecho un boquete en la pared, arreglarlo porfa",
                                                                                    "urgencia": 3
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
                            description = "No se han encontrado incidencias relacionadas",
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
                                                                            "detail": "No se han encontrado incidencias",
                                                                            "instance": "/api/incidencia/tecnico/1/verGestionadas"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping("/tecnico/{idTecnico}/verGestionadas")
    public GetIncidenciaListDto findAllByTecnico(@PathVariable Long idTecnico) {

        return GetIncidenciaListDto.of(incidenciaService.findAllByTecnico(idTecnico));
    }

    @Operation(summary = "Se ven los detalles de una incidencia con los técnicos que la gestionan")
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Se ven los detalles correctamente",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = GetIncidenciaDetailsAdminDto.class),
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
                                                                        "notas": [],
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
                                                                        },
                                                                        "tecnicosGestionan": [
                                                                            {
                                                                                "nombre": "Lucas",
                                                                                "username": "lucas_martinez123",
                                                                                "email": "lucas.martinez23@triana.salesianos.edu",
                                                                                "role": "",
                                                                                "tipoUsuario": "técnico"
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
                        responseCode = "401",
                        description = "No tienes los permisos para ver los detalles",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ProblemDetail.class),
                                        examples = {
                                                @ExampleObject(
                                                        value = """
                                                                    {
                                                                        "type": "about:blank",
                                                                        "title": "Pas permiso no concedido",
                                                                        "status": 401,
                                                                        "detail": "No se ha encontrado un usuario PAS con el id: 2",
                                                                        "instance": "/api/incidencia/admin/2/detalles/1"
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
                                                                        "detail": "No se han encontrado incidencias para el usuario con ID: 2",
                                                                        "instance": "/api/incidencia/admin/1/detalles/2"
                                                                    }
                                                                """
                                                )
                                        }
                                )
                        }
                )
        }
    )
    @GetMapping("/admin/{idAdmin}/detalles/{idIncidencia}")
    public GetIncidenciaDetailsAdminDto findByIdDetailsTecnicosGestionan(@PathVariable Long idAdmin, @PathVariable Long idIncidencia) {

        return GetIncidenciaDetailsAdminDto.of(incidenciaService.findByIdAdmin(idAdmin, idIncidencia));
    }

    @Operation(summary = "Se buscan todas las incidencias que se no se encuentran cerradas",
    description = """
            El método tiene un parámetro de petición llamado filtro, cuyo valor predeterminado es 'no'. En caso de querer filtrar por nombre de categoría,\
             se tendrá que indicar con el patrón 'categoria-nombrecategoria'.
            
            En caso de querer filtrar por el estado de la incidencia, se tendrá que indicar con el patrón 'estado-nombreestado'.
            
            En caso de querer filtrar por ubicación de la incidencia, se tendrá que indicar con el patrón 'ubicacion-nombreubicacion'.
            
            Si se quiere ordenar todas las incidencias por fecha, se tendrá que indicar con el parámetro de petición llamado ordenarFecha y asignarle
             de valor true.
            
            No se pueden ordenar por fecha los filtrados.
            
            """)
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
                            responseCode = "401",
                            description = "No tienes permisos para ver todas las incidencias",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Pas permiso no concedido",
                                                                            "status": 401,
                                                                            "detail": "No se ha encontrado un usuario PAS con el id: 2",
                                                                            "instance": "/api/incidencia/admin/2"
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
                                                                            "detail": "No se han encontrado incidencias",
                                                                            "instance": "/api/incidencia/admin/1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping("/admin/{idAdmin}")
    public GetIncidenciaListDto findAll(@PathVariable Long idAdmin,
                                        @Parameter(
                                                description = "Posible valor para el filtro",
                                                schema = @Schema(type = "string"),
                                                example = "categoria-ordenadores"
                                        )
                                        @RequestParam(required = false, defaultValue = "no") String filtro,
                                        @Parameter(
                                                description = "Posible valor para el ordenarFecha",
                                                schema = @Schema(type = "boolean"),
                                                example = "false"
                                        )
                                        @RequestParam(required = false) boolean ordenarFecha) {

        return GetIncidenciaListDto.of(incidenciaService.findAll(idAdmin, filtro, ordenarFecha));
    }


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
    @GetMapping("/usuario/{idUsuario}")
    public GetIncidenciaListDto findIncidenciasByUsuario(@PathVariable Long idUsuario) {

        return GetIncidenciaListDto.of(incidenciaService.findAllByUsuario(idUsuario));
    }


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
    @GetMapping("/usuario/{idUsuario}/detalles/{idIncidencia}")
    public GetIncidenciaDetailsDto findIncidenciaByUsuarioAndId(@PathVariable Long idUsuario, @PathVariable Long idIncidencia) {

        return GetIncidenciaDetailsDto.of(incidenciaService.findByIdAndUsuario(idUsuario, idIncidencia));
    }


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
    @PutMapping("/usuario/{usuarioId}/editar/{idIncidencia}")
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
    @DeleteMapping("/usuario/{idUsuario}/borrar/{idIncidencia}")
    public ResponseEntity<?> deleteByIdIncidenciaAbierta(@PathVariable Long idUsuario, @PathVariable Long idIncidencia) {

        incidenciaService.deleteById(idUsuario, idIncidencia);

        return ResponseEntity.noContent().build();
    }


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
    @PutMapping("/{idIncidencia}/usuario/{idUsuario}/addNota")
    public GetIncidenciaDetailsDto addNota(@PathVariable Long idIncidencia, @PathVariable Long idUsuario,
                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                   description = "Contenido de la nota a añadir",
                                                   required = true,
                                                   content = {
                                                           @Content(
                                                                   mediaType = "application/json",
                                                                   schema = @Schema(implementation = CreateNotaDto.class),
                                                                   examples = {
                                                                           @ExampleObject(
                                                                                   value = """
                                                                                            {
                                                                                                "contenido": "Ha dejado de arder, pero está todo chamuscao"
                                                                                            }
                                                                                           """
                                                                           )
                                                                   }
                                                           )
                                                   }
                                           )
                                           @RequestBody CreateNotaDto notaDto) {

        return GetIncidenciaDetailsDto.of(incidenciaService.addNota(idUsuario, idIncidencia, notaDto));
    }


    @Operation(summary = "Se borra una nota a una incidencia que no está cerrada")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se borra correctamente la nota de la incidencia",
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
                                                                    "descripcion": "No sé, el ordenador está ardiendo socorro ayuda ya porfavor",
                                                                    "estado": "ABIERTA",
                                                                    "urgencia": 5,
                                                                    "categoria": "Ordenadores",
                                                                    "notas": [],
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
                            description = "No se ha encontrado la incidencia a la que borrar la nota",
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
                                                                         "instance": "/api/incidencia/1/usuario/2/borrarnota/1"
                                                                    }
                                                                   """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PutMapping("/{idIncidencia}/usuario/{idUsuario}/borrarnota/{idNota}")
    public GetIncidenciaDetailsDto eliminarNota(@PathVariable Long idUsuario, @PathVariable Long idIncidencia,
                                   @PathVariable Long idNota) {

        return GetIncidenciaDetailsDto.of(incidenciaService.eliminarNota(idUsuario, idIncidencia, idNota));
    }

    @Operation(summary = "Se registra una incidencia nueva",
    description = """
            Para registrar una incidencia nueva se deberá incluir el id del usuario que vaya a
            registrar la incidencia, el nombre de la categoría que se le va a asignar, el id del equipo
            al que irá relacionada (en el caso de no estar relacionada a un equipo se le pasará como id
            -1) y por último el nombre de la ubicación en la que se situará.
            """)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se ha registrado la incidencia correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetIncidenciaDetailsDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "fecha": "2025-02-05",
                                                                            "titulo": "Boquete pared",
                                                                            "descripcion": "Un nota ha hecho un boquete en la pared, arreglarlo porfa",
                                                                            "estado": "ABIERTA",
                                                                            "urgencia": 3,
                                                                            "categoria": "Ordenadores",
                                                                            "notas": [],
                                                                            "equipo": null,
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
                            description = "No se ha encontrado el usuario para registrar la incidencia",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Usuario no encontrado",
                                                                            "status": 404,
                                                                            "detail": "No se ha encontrado un usuario con el ID: 3",
                                                                            "instance": "/api/incidencia/usuario/3/crearIncidencia/categoria/ordenadores/equipo/-1/ubicacion/Aula%201"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado la categoría a la que añadir",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Categoria no encontrada",
                                                                            "status": 404,
                                                                            "detail": "No se ha encontrado la categoria inexistente",
                                                                            "instance": "/api/incidencia/usuario/2/crearIncidencia/categoria/inexistente/equipo/-1/ubicacion/Aula%201"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado la ubicación de la incidencia",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Ubicación no encontrada",
                                                                            "status": 404,
                                                                            "detail": "No se han encontrado ubicaciones",
                                                                            "instance": "/api/incidencia/usuario/1/crearIncidencia/categoria/ordenadores/equipo/-1/ubicacion/Aula%202"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping("/usuario/{idUsuario}/crearIncidencia/categoria/{categoria}/equipo/{idEquipo}/ubicacion/{ubicacion}")
    public ResponseEntity<GetIncidenciaDetailsDto> crearIncidencia(@PathVariable Long idUsuario, @PathVariable String categoria,
                                                   @PathVariable Long idEquipo, @PathVariable String ubicacion,
                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                           description = "Datos de la incidencia a crear",
                                                           required = true,
                                                           content = {
                                                                   @Content(
                                                                           mediaType = "application/json",
                                                                           schema = @Schema(implementation = CreateIncidenciaDto.class),
                                                                           examples = {
                                                                                   @ExampleObject(
                                                                                           value = """
                                                                                                    {
                                                                                                      "titulo": "Boquete pared",
                                                                                                      "descripcion": "Un nota ha hecho un boquete en la pared, arreglarlo porfa",
                                                                                                      "urgencia": 3
                                                                                                    }
                                                                                                   """
                                                                                   )
                                                                           }
                                                                   )
                                                           }
                                                   )
                                                                   @RequestBody CreateIncidenciaDto incidenciaDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                GetIncidenciaDetailsDto.of(incidenciaService.save(idUsuario, incidenciaDto, categoria, idEquipo, ubicacion))
        );
    }


    @Operation(summary = "Un técnico selecciona una incidencia para poder gestionarla")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ha seleccionado la incidencia correctamente",
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
                                                                            "descripcion": "No sé, el ordenador está ardiendo socorro ayuda ya porfavor",
                                                                            "estado": "ABIERTA",
                                                                            "urgencia": 5,
                                                                            "categoria": "Ordenadores",
                                                                            "notas": [],
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
                            responseCode = "401",
                            description = "El usuario que quiere gestionar no es técnico",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Tecnico permiso no concedido",
                                                                            "status": 401,
                                                                            "detail": "El usuario debe de ser un técnico para acceder",
                                                                            "instance": "/api/incidencia/tecnico/5/seleccionar/1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "El técnico ya está gestionando esa incidencia",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "No se puede seleccionar esta incidencia",
                                                                            "status": 400,
                                                                            "detail": "El técnico con ID: 2 ya está gestionando la incidencia con ID: 1",
                                                                            "instance": "/api/incidencia/tecnico/2/seleccionar/1"
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
                                                                            "detail": "No se han encontrado incidencias para el usuario con ID: 12",
                                                                            "instance": "/api/incidencia/tecnico/2/seleccionar/12"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )

            }
    )
    @PutMapping("/tecnico/{idTecnico}/seleccionar/{idIncidencia}")
    public GetIncidenciaDetailsDto seleccionarIncidencia(@PathVariable Long idTecnico, @PathVariable Long idIncidencia){

        return GetIncidenciaDetailsDto.of(incidenciaService.seleccionarIncidencia(idTecnico, idIncidencia));
    }

    @PutMapping("/tecnico/{idTecnico}/incidencia/{idIncidencia}/{estado}")
    public GetIncidenciaDetailsDto cambiarEstadoIncidencia(@PathVariable Long idTecnico, @PathVariable Long idIncidencia,
                                                           @PathVariable String estado){
        return GetIncidenciaDetailsDto.of(incidenciaService.cambiarEstado(idTecnico, idIncidencia, estado));
    }

}
