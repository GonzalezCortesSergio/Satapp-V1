package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Tecnico extends Usuario{

    @ManyToMany(mappedBy = "tecnicosGestionan", fetch = FetchType.EAGER)
    private Set<Incidencia> incidenciasGestiona;
}
