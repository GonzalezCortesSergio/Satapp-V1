package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.CreateEquipoDto;
import com.salesianostriana.dam.satapp.dto.GetEquipoDto;
import com.salesianostriana.dam.satapp.dto.GetEquipoListDto;
import com.salesianostriana.dam.satapp.model.Equipo;
import com.salesianostriana.dam.satapp.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/equipo")
@RequiredArgsConstructor
@Tag(
        name = "Equipo",
        description = "Controlador de Equipos, para poder realizar todas sus operaciones de gestión"
)
public class EquipoController {

    private final EquipoService equipoService;


    @GetMapping
    @Operation(summary = "Se muestran los equipos que no tienen incidencia o las incidencias que tiene están cerradas")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ha encontrado 1 o más equipos",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetEquipoListDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "count": 1,
                                                                            "results": [
                                                                                {
                                                                                    "id": 2,
                                                                                    "nombre": "Aire acondicionado",
                                                                                    "caracteristicas": "Un aire acondicionado para que los de DAM en verano no pasen calor",
                                                                                    "ubicacion": " "
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
                            description = "No se han encontrado equipos sin incidencia o incidencias cerradas",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Equipo no encontrado",
                                                                            "status": 404,
                                                                            "detail": "No se han encontrado equipos",
                                                                            "instance": "/api/equipo"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public GetEquipoListDto findAllWithNoIncidencia() {

        return GetEquipoListDto.of(equipoService.findAllWithNoIncidencia());
    }


    @PostMapping("/admin/{idAdmin}/crear")
    @Operation(summary = "Se agrega un equipo nuevo")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se ha creado un nuevo equipo correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetEquipoDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 1,
                                                                            "nombre": "Aire acondicionado",
                                                                            "catacteristicas": "Un aire acondicionado para que los de DAM en verano no pasen calor",
                                                                            "ubicacion": " "
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                           responseCode = "401",
                            description = "El usuario que quiere añadir un nuevo equipo no tiene los permisos",
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
                                                                        "instance": "/api/equipo/admin/2/crear"
                                                                    }
                                                                   """
                                                   )
                                           }
                                   )
                            }
                    )
            }
    )
    public ResponseEntity<GetEquipoDto> crearEquipo(@PathVariable Long idAdmin,
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      description = "Equipo a crear",
                                                      required = true,
                                                      content = {
                                                              @Content(
                                                                      mediaType = "application/json",
                                                                      schema = @Schema(implementation = CreateEquipoDto.class),
                                                                      examples = {
                                                                              @ExampleObject(
                                                                                      value = """
                                                                                                {
                                                                                                    "nombre": "Aire acondicionado",
                                                                                                    "caracteristicas": "Un aire acondicionado para que los de DAM en verano no pasen calor"
                                                                                                }
                                                                                              """
                                                                              )
                                                                      }
                                                              )
                                                      }
                                              )
                                              @RequestBody CreateEquipoDto equipoDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetEquipoDto.of(equipoService.save(idAdmin, equipoDto)));
    }

    @PutMapping("/admin/{idAdmin}/edit/{idEquipo}")
    @Operation(summary = "Se edita un equipo")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ha editado el equipo correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetEquipoDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 1,
                                                                            "nombre": "Portátil",
                                                                            "caracteristicas": "Portátil to wapo pa los nenes de primero",
                                                                            "ubicacion": " "
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene los permisos para editar un equipo",
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
                                                                            "instance": "/api/equipo/admin/2/edit/1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado ningún equipo",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Equipo no encontrado",
                                                                            "status": 404,
                                                                            "detail": "No se ha encontrado un equipo con el ID: 2",
                                                                            "instance": "/api/equipo/admin/1/edit/2"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public GetEquipoDto edit(@PathVariable Long idAdmin, @PathVariable Long idEquipo,
                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                               description = "Datos a editar del equipo",
                               required = true,
                               content = {
                                       @Content(
                                               mediaType = "application/json",
                                               schema = @Schema(implementation = CreateEquipoDto.class),
                                               examples = {
                                                       @ExampleObject(
                                                               value = """
                                                                        {
                                                                            "nombre": "Portátil",
                                                                            "caracteristicas": "Portátil to wapo pa los nenes de primero"
                                                                        }
                                                                       """
                                                       )
                                               }
                                       )
                               }
                       )
                       @RequestBody CreateEquipoDto equipoDto) {

        return GetEquipoDto.of(equipoService.edit(idAdmin, idEquipo, equipoDto));
    }

    @DeleteMapping("/admin/{idAdmin}/delete/{idEquipo}")
    @Operation(summary = "Se borra un equipo con soft delete")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Se ha eliminado el equipo correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema
                                    )
                            }
                    ),
                    @ApiResponse(
                           responseCode = "401",
                            description = "No tienes permisos para hacer esta acción",
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
                                                                        "instance": "/api/equipo/admin/2/delete/2"
                                                                    }
                                                                   """
                                                   )
                                           }
                                   )
                            }
                    )
            }
    )
    public ResponseEntity<?> deleteEquipo(@PathVariable Long idAdmin, @PathVariable Long idEquipo) {

        equipoService.remove(idAdmin, idEquipo);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/{idAdmin}/cambiar/{idEquipo}/ubicacion/{nombreUbicacion}")
    @Operation(summary = "Se cambia un equipo de ubicación")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ha cambiado de ubicación correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetEquipoDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 2,
                                                                            "nombre": "Aire acondicionado",
                                                                            "caracteristicas": "Un aire acondicionado para que los de DAM en verano no pasen calor",
                                                                            "ubicacion": "Aula 1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No tienes permisos para hacer esta acción",
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
                                                                            "instance": "/api/equipo/admin/2/cambiar/2/ubicacion/Aula%201"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado el equipo a cambiar de ubicación",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Equipo no encontrado",
                                                                            "status": 404,
                                                                            "detail": "No se ha encontrado un equipo con el ID: 2",
                                                                            "instance": "/api/equipo/admin/1/cambiar/2/ubicacion/Aula%202"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado la ubicación a cambiar",
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
                                                                            "instance": "/api/equipo/admin/1/cambiar/1/ubicacion/Aula%202"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public GetEquipoDto cambiarUbicacion(@PathVariable Long idAdmin, @PathVariable Long idEquipo, @PathVariable String nombreUbicacion) {

        return GetEquipoDto.of(equipoService.cambiarUbicacion(idAdmin, idEquipo, nombreUbicacion));
    }
 }
