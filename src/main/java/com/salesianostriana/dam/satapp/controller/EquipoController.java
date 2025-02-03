package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.CreateEquipoDto;
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
                                                                                    "caracteristicas": "Un aire acondicionado para que los de DAM en verano no pasen calor"
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
                                            schema = @Schema(implementation = Equipo.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 1,
                                                                            "nombre": "Aire acondicionado",
                                                                            "catacteristicas": "Un aire acondicionado para que los de DAM en verano no pasen calor",
                                                                            "ubicacion": null
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
    public ResponseEntity<Equipo> crearEquipo(@PathVariable Long idAdmin,
                                              @Schema(
                                                      description = "Equipo a añadir",
                                                      implementation = CreateEquipoDto.class,
                                                      example= """
                                                                {
                                                                    "nombre": "Aire acondicionado",
                                                                    "caracteristicas": "Un aire acondicionado para que los de DAM en verano no pasen calor"
                                                                }
                                                              """
                                              )@RequestBody CreateEquipoDto equipoDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(equipoService.save(idAdmin, equipoDto));
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
                                            schema = @Schema(implementation = Equipo.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 1,
                                                                            "nombre": "Portátil",
                                                                            "caracteristicas": "Portátil to wapo pa los nenes de primero",
                                                                            "ubicacion": null
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
    public Equipo edit(@PathVariable Long idAdmin, @PathVariable Long idEquipo,
                       @Schema(description = "Nombre y descripción a cambiar",
                       implementation = CreateEquipoDto.class
                       )@RequestBody CreateEquipoDto equipoDto) {

        return equipoService.edit(idAdmin, idEquipo, equipoDto);
    }
}
