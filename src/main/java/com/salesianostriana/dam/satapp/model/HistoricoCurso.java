package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@IdClass(HistoricoCursoPK.class)
public class HistoricoCurso {

    @Id
    @GeneratedValue
    private Long id;
    @Id
    @ManyToOne
    @JoinColumn(name = "alumno_id",
            foreignKey = @ForeignKey(name = "fk_historico_curso_alumno"))
    private Alumno alumno;

    private String curso;

    private String cursoEscolar;
}
