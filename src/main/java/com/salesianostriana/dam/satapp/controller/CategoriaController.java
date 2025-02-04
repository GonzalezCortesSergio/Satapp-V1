package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.GetCategoriaDto;
import com.salesianostriana.dam.satapp.service.CategoriaService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categoria")
@RequiredArgsConstructor
@Tag(name = "Categoria",
        description = "Controlador de categorias, para poder realizar todas sus operaciones de gestión")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping("admin/{idAdmin}/crear/{nombre}")
    @Operation(summary = "Solo un usuario PAS puede crear categorias")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para crear una categoría",
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
                                                                            "instance": "/api/categoria/admin/2/crear/noseapaga1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se ha creado la categoría",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetCategoriaDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "nombre": "noseapaga",
                                                                            "categoriaPadre": " "
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Esta categoria ya existe",
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
                                                                            "detail": "Esta categoría ya existe",
                                                                            "instance": "/api/categoria/admin/1/crear/noenciende"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<GetCategoriaDto> crearCategoria(@PathVariable Long idAdmin, @PathVariable String nombre){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetCategoriaDto.of(categoriaService.crearCategoria(idAdmin,nombre)) );
    }

    @PostMapping("admin/{idAdmin}/crear/{nombre}/{nombreHija}")
    @Operation(summary = "Solo un usuario PAS puede crear categorias hijas")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para crear una categoría hija",
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
                                                                            "instance": "/api/categoria/admin/2/crear/noseapaga/noreinicia"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se ha creado la categoría hija",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetCategoriaDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "nombre": "noreinicia",
                                                                            "categoriaPadre": "noseapaga"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Esta categoria ya existe",
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
                                                                            "detail": "Esta categoría ya existe",
                                                                            "instance": "/api/categoria/admin/1/crear/noseapaga/noreinicia"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<GetCategoriaDto> crearCategoriaHija(@PathVariable Long idAdmin, @PathVariable String nombre,
                                                              @PathVariable String nombreHija){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetCategoriaDto.of(categoriaService.crearCategoriaHija(idAdmin, nombre, nombreHija)));
    }

}
