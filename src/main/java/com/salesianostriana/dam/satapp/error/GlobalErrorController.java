package com.salesianostriana.dam.satapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncidenciaNotFoundException.class)
    public ProblemDetail handleIncidenciaNotFoundException(IncidenciaNotFoundException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());

        detail.setTitle("Incidencia no encontrada");

        return detail;
    }


    @ExceptionHandler(UsuarioNotFoundException.class)
    public ProblemDetail handleUsuarioNotFoundException(UsuarioNotFoundException exception) {

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

        detail.setTitle("Usuario no encontrado");

        return detail;
    }
}
