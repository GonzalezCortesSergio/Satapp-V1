package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "tecnicosGestionan", fetch = FetchType.LAZY)
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Set<Incidencia> incidenciasGestiona = new HashSet<>();
}
