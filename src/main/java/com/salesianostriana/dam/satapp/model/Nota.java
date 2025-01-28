package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@IdClass(NotaPK.class)
public class Nota {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate fecha;
    private String contenido;
    private String autor;

    @ManyToOne
    @JoinColumn(
            foreignKey = @ForeignKey(name = "fk_nota_incidencia")
    )
    @Id
    private Incidencia incidencia;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Nota nota = (Nota) o;
        return getId() != null && Objects.equals(getId(), nota.getId())
                && getIncidencia() != null && Objects.equals(getIncidencia(), nota.getIncidencia());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, incidencia);
    }
}
