package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Getter
@Setter
@DiscriminatorValue(value = "alumno")
public class Alumno extends Usuario{

    @OneToMany(
            mappedBy = "alumno",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private List<HistoricoCurso> listaHistoricoCurso = new ArrayList<>();

}
