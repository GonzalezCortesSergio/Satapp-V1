package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class HistoricoCurso {

    @Id
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    private String curso;

    private String cursoEscolar;
}
