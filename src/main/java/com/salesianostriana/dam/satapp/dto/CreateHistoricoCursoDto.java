package com.salesianostriana.dam.satapp.dto;

import com.salesianostriana.dam.satapp.model.HistoricoCurso;

public record CreateHistoricoCursoDto(
        String curso,
        String cursoEscolar
) {
    public HistoricoCurso toHistoricoCurso(){
        return HistoricoCurso.builder()
                .curso(this.curso)
                .cursoEscolar(this.cursoEscolar)
                .build();
    }
}
