package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.CreateUsuarioDto;
import com.salesianostriana.dam.satapp.dto.GetIncidenciaDto;
import com.salesianostriana.dam.satapp.model.Usuario;
import com.salesianostriana.dam.satapp.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuario",
        description = "Controlador de usuarios, para poder realizar todas sus operaciones de gestión")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/admin/{idAdmin}/crear/{tipoUsuario}")
    @Operation(summary = "Un usuario PAS crea otros usuarios")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para crear otro usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        [
                                                                            {
                                                                                "type": "about:blank",
                                                                                "title": "Pas permiso no concedido",
                                                                                "status": 401,
                                                                                "detail": "No se ha encontrado un usuario PAS con el id: 2",
                                                                                "instance": "/api/usuario/admin/2/crear/alumno"
                                                                            }
                                                                        ]
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se ha creado el usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Usuario.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 4,
                                                                            "nombre": "maria",
                                                                            "username": "mariaA",
                                                                            "password": "1234",
                                                                            "email": "a@a",
                                                                            "role": "noAdmin",
                                                                            "listaHistoricoCurso": []
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se puede crear ese tipo de usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Tipo usuario no permitido",
                                                                            "status": 400,
                                                                            "detail": "No se puede crear ese tipo",
                                                                            "instance": "/api/usuario/admin/1/crear/alumn"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Usuario>crearUsuario(@PathVariable Long idAdmin, @RequestBody CreateUsuarioDto createUsuarioDto,
                                               @PathVariable String tipoUsuario,@RequestParam(required = false) String tipoPersonal){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        usuarioService.crearUsuario(idAdmin, createUsuarioDto, tipoUsuario, tipoPersonal));
    }

}
