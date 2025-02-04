package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.HistoricoCurso;

public record GetHistoricoCursoDto(
        String curso,
        String cursoEscolar
) {

    public static GetHistoricoCursoDto of(HistoricoCurso historicoCurso){
        return new GetHistoricoCursoDto(
                historicoCurso.getCurso(),
                historicoCurso.getCursoEscolar()
        );
    }

}
