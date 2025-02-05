package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class IncidenciaTecnicoPK implements Serializable {

    private static final long serialVersionUID= 1L;

    private Long incidencia_id;
    private Long tecnico_id;
}
