package com.salesianostriana.dam.satapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalErrorController extends ResponseEntityExceptionHandler {

    //errores incidencias
    @ExceptionHandler(IncidenciaNotFoundException.class)
    public ProblemDetail handleIncidenciaNotFoundException(IncidenciaNotFoundException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());

        detail.setTitle("Incidencia no encontrada");

        return detail;
    }

    @ExceptionHandler(IncidenciaNotAbiertaException.class)
    public ProblemDetail handleIncidenciaNotAbiertaException(IncidenciaNotAbiertaException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                exception.getMessage());
        detail.setTitle("Incidencia no abierta");

        return detail;
    }

    //errores ususario
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ProblemDetail handleUsuarioNotFoundException(UsuarioNotFoundException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        detail.setTitle("Usuario no encontrado");

        return detail;
    }

    @ExceptionHandler(PasPermisoDenegadoException.class)
    public ProblemDetail handlePasPermisoDenegadoException(PasPermisoDenegadoException exception){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        detail.setTitle("Pas permiso no concedido");

        return detail;
    }

    @ExceptionHandler(TipoUsusarioNoPermitidoException.class)
    public ProblemDetail handleTipoUsusarioNoPermitidoException(TipoUsusarioNoPermitidoException exception){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        detail.setTitle("Tipo usuario no permitido");

        return detail;
    }

    @ExceptionHandler(UsuarioPermisoDenegadoException.class)
    public ProblemDetail handleUsuarioPermisoDenegadoException(UsuarioPermisoDenegadoException exception){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        detail.setTitle("Usuario permiso no concedido");

        return detail;
    }


    //errores equipo

    @ExceptionHandler(EquipoNotFoundException.class)
    public ProblemDetail handleEquipoNotFoundException(EquipoNotFoundException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        detail.setTitle("Equipo no encontrado");

        return detail;
    }


    //errores ubicación

    @ExceptionHandler(NombreRepetidoException.class)
    public ProblemDetail handleNombreRepetidoException(NombreRepetidoException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                exception.getMessage());

        detail.setTitle("Nombre repetido");

        return detail;
    }

    @ExceptionHandler(UbicacionNotFoundException.class)
    public ProblemDetail handleUbicacionNotFoundException(UbicacionNotFoundException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());

        detail.setTitle("Ubicación no encontrada");

        return detail;
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ProblemDetail handleCategoriaNotFoundException(CategoriaNotFoundException exception){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        detail.setTitle("Categoria no encontrada");

        return detail;
    }

    @ExceptionHandler(TecnicoPermisoDenegadoException.class)
    public ProblemDetail handleTecnicoPermisoDenegadoException(TecnicoPermisoDenegadoException exception){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        detail.setTitle("Tecnico permiso no concedido");

        return detail;
    }

}
