package com.salesianostriana.dam.satapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoCursoPK {

    private Long id;

    private Alumno alumno;
}
