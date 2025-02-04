package com.salesianostriana.dam.satapp.controller;

import com.salesianostriana.dam.satapp.dto.CreateHistoricoCursoDto;
import com.salesianostriana.dam.satapp.dto.CreateUsuarioDto;
import com.salesianostriana.dam.satapp.dto.GetAlumnoDto;
import com.salesianostriana.dam.satapp.dto.GetUsuarioDto;
import com.salesianostriana.dam.satapp.model.HistoricoCurso;
import com.salesianostriana.dam.satapp.model.Usuario;
import com.salesianostriana.dam.satapp.service.UsuarioService;
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

import java.util.List;
import java.util.Optional;

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
                                                                   
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Pas permiso no concedido",
                                                                            "status": 401,
                                                                            "detail": "No se ha encontrado un usuario PAS con el id: 2",
                                                                            "instance": "/api/usuario/admin/2/crear/alumno"
                                                                        }
                                                                        
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

    @PutMapping("/admin/{idAdmin}/editar/{id}")
    @Operation(summary = "Un usuario PAS edita otros usuarios")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para editar otro usuario",
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
                                                                            "instance": "/api/usuario/admin/2/editar/2"
                                                                        }
                                                                        
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ha editado el usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Usuario.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "id": 2,
                                                                            "nombre": "lucas",
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
                            responseCode = "404",
                            description = "Usuario no encontrado",
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
                                                                            "detail": "No se ha encontrado ningun usuario con la id:  3",
                                                                            "instance": "/api/usuario/admin/1/editar/3"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public Usuario editarPas(@PathVariable Long idAdmin, @RequestBody CreateUsuarioDto createUsuarioDto, @PathVariable Long id){
        return usuarioService.editarUsuario(idAdmin, createUsuarioDto,id);
    }

    @GetMapping("/admin/{idAdmin}/verTodosUsuarios")
    @Operation(summary = "Un usuario PAS puede ver todos los usuarios")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para ver los usuarios",
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
                                                                            "instance": "/api/usuario/admin/2/verTodosUsuarios"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ven todos los usuarios",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetUsuarioDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        [
                                                                            {
                                                                                "nombre": "Pablo",
                                                                                "username": "pablo_martinez123",
                                                                                "email": "pablo.martinez23@triana.salesianos.edu",
                                                                                "role": "",
                                                                                "tipoUsuario": "personal"
                                                                            }
                                                                        ]
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public List<GetUsuarioDto> findAllUsuarios(@PathVariable Long idAdmin){
        return usuarioService.findAll(idAdmin)
                .stream()
                .map(GetUsuarioDto::of)
                .toList();
    }


    @GetMapping("/admin/{idUsuario}/verUsuario/{id}")
    @Operation(summary = "Un usuario PAS puede ver todos los usuarios y un usuario solo puede verse a sí mismo")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para ver el usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Usuario permiso no concedido",
                                                                            "status": 401,
                                                                            "detail": "No tiene permiso para ver este usuario.",
                                                                            "instance": "/api/usuario/admin/2/verUsuario/1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ve el usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetUsuarioDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "nombre": "maria",
                                                                            "username": "mariaA",
                                                                            "email": "a@a",
                                                                            "role": "noAdmin",
                                                                            "tipoUsuario": "alumno"
                                                                        }
                                 
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe el usuario",
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
                                                                            "detail": "No hay usuario con la id: 3",
                                                                            "instance": "/api/usuario/admin/2/verUsuario/3"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public GetUsuarioDto findById(@PathVariable Long idUsuario, @PathVariable Long id) {

        return GetUsuarioDto.of(usuarioService.findById(id, idUsuario));
    }

    @DeleteMapping("/admin/{idUsuario}/delete/{id}")
    @Operation(summary = "Eliminar un usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para ver el usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProblemDetail.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                        {
                                                                            "type": "about:blank",
                                                                            "title": "Usuario permiso no concedido",
                                                                            "status": 401,
                                                                            "detail": "No tiene permiso para eliminar este usuario.",
                                                                            "instance": "/api/usuario/admin/3/delete/1"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Se borra el usuario",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema,
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                   
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe el usuario",
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
                                                                            "detail": "No se encontró el usuario con id: 2",
                                                                            "instance": "/api/usuario/admin/1/delete/2"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<?> deleteByIdUsuario(@PathVariable Long idUsuario, @PathVariable Long id) {

        usuarioService.deleteById(idUsuario, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/{idAdmin}/aniadirhistoricocurso/{idAlumno}")
    @Operation(summary = "Añadir un historico curso")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "El usuario no tiene permiso para añadir un historico curso",
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
                                                                            "instance": "/api/usuario/admin/2/aniadirhistoricocurso/2"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se agrega el historico curso",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetAlumnoDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    {
                                                                        "nombre": "maria",
                                                                        "username": "mariaA",
                                                                        "listaHistorico": [
                                                                            {
                                                                                "curso": "1",
                                                                                "cursoEscolar": "DAM"
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
                            description = "No existe el usuario al que se le intenta añadir el historico",
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
                                                                            "detail": "No hay alumno con la id: 4",
                                                                            "instance": "/api/usuario/admin/1/aniadirhistoricocurso/4"
                                                                        }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public GetAlumnoDto aniadirHistoricoCurso(@PathVariable Long idAlumno, @PathVariable Long idAdmin,
                                              @RequestBody CreateHistoricoCursoDto createHistoricoCursoDto){

        return GetAlumnoDto.of(usuarioService.aniadirHistoricoCurso(idAlumno, idAdmin, createHistoricoCursoDto));
    }


}
