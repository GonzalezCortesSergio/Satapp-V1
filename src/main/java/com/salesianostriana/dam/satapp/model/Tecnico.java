package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@DiscriminatorValue(value = "tecnico")
public class Tecnico extends Usuario{

    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Set<IncidenciaTecnico> incidenciasGestiona = new HashSet<>();
}
