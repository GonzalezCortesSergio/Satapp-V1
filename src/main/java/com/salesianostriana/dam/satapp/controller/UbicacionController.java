package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.model.Ubicacion;
import com.salesianostriana.dam.satapp.service.UbicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ubicacion")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionService ubicacionService;


    @PostMapping("/admin/{idAdmin}/crear/{nombre}")
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
    public ResponseEntity<Ubicacion> save(@PathVariable Long idAdmin, @PathVariable String nombre) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionService.save(idAdmin,nombre));
    }
}
