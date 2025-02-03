package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.CreateEquipoDto;
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
}
