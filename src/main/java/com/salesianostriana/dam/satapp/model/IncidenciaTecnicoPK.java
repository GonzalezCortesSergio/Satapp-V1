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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        IncidenciaTecnicoPK that = (IncidenciaTecnicoPK) o;
        return getIncidencia_id() != null && Objects.equals(getIncidencia_id(), that.getIncidencia_id())
                && getTecnico_id() != null && Objects.equals(getTecnico_id(), that.getTecnico_id());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(incidencia_id, tecnico_id);
    }
}
