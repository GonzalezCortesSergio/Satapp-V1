package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.GetUbicacionListDto;
import com.salesianostriana.dam.satapp.model.Ubicacion;
import com.salesianostriana.dam.satapp.service.UbicacionService;
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
@RequestMapping("/api/ubicacion")
@RequiredArgsConstructor
@Tag(name = "Ubicación",
description = "Controlador de ubicaciones para poder realizar sus operaciones de gestión")
public class UbicacionController {

    private final UbicacionService ubicacionService;



    @Operation(summary = "Muestra todas las ubicaciones")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se muestran las ubicaciones correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetUbicacionListDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "count": 2,
                                                                            "results": [
                                                                                "2º DAM",
                                                                                "Aula 1"
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
                            description = "No se han encontrado ubicaciones",
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
                                                                            "instance": "/api/ubicacion"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping
    public GetUbicacionListDto findAll() {

        return GetUbicacionListDto.of(ubicacionService.findAll());
    }


    @Operation(summary = "Crea una ubicación nueva")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se ha creado la ubicación correctamente",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Ubicacion.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 3,
                                                                            "nombre": "2º DAM"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Esa ubicación ya existe",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Nombre repetido",
                                                                            "status": 400,
                                                                            "detail": "Esta ubicación ya existe",
                                                                            "instance": "/api/ubicacion/admin/1/crear/2%C2%BA%20DAM"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No tienes los permisos para crear una ubicación",
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
                                                                            "instance": "/api/ubicacion/admin/2/crear/2%C2%BA%20DAM"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping("/admin/{idAdmin}/crear/{nombre}")
    public ResponseEntity<Ubicacion> save(@PathVariable Long idAdmin, @PathVariable String nombre) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionService.save(idAdmin,nombre));
    }


    @Operation(summary = "Se borra una ubicación por nombre")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Se borra la ubicación correctamente",
                            content = {
                                    @Content
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No tienes los permisos para borrar la ubicación",
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
                                                                            "instance": "/api/ubicacion/admin/2/delete/Aula%201"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/admin/{idAdmin}/delete/{nombre}")
    public ResponseEntity<?> deleteByNombre(@PathVariable Long idAdmin, @PathVariable String nombre) {

        ubicacionService.deleteByNombre(idAdmin, nombre);

        return ResponseEntity.noContent().build();
    }
}
