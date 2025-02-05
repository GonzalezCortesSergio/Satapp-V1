package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IncidenciaTecnico {

    @EmbeddedId
    private IncidenciaTecnicoPK incidenciaTecnicoPK = new IncidenciaTecnicoPK();

    @ManyToOne
    @MapsId("incidencia_id")
    @JoinColumn(name = "incidencia_id")
    private Incidencia incidencia;

    @ManyToOne
    @MapsId("tecnico_id")
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    private boolean tecnicoResponsable;

    public void addToIncidencia(Incidencia incidencia) {

        incidencia.getTecnicosGestionan().add(this);
        this.setIncidencia(incidencia);
    }

    public void addToTecnico(Tecnico tecnico) {

        tecnico.getIncidenciasGestiona().add(this);
        this.setTecnico(tecnico);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        IncidenciaTecnico that = (IncidenciaTecnico) o;
        return getIncidenciaTecnicoPK() != null && Objects.equals(getIncidenciaTecnicoPK(), that.getIncidenciaTecnicoPK());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(incidenciaTecnicoPK);
    }
}
