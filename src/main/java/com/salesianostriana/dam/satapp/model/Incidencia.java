package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Incidencia {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate fecha;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(value = EnumType.STRING)
    private Estado estado;

    @Column(scale = 1)
    private int urgencia;

    @ManyToMany
    @JoinTable(
            name = "incidencia_tecnico",
            joinColumns = @JoinColumn(name = "incidencia_id"),
            inverseJoinColumns = @JoinColumn(name = "tecnico_id"),
            foreignKey = @ForeignKey(name = "fk_incidencia_tecnico_incidencia"),
            inverseForeignKey = @ForeignKey(name = "fk_incidencia_tecnico_tecnico")
    )
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Set<Tecnico> tecnicosGestionan = new HashSet<>();

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Categoria categoria;

    @OneToMany(
            mappedBy = "incidencia",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private List<Nota> listaNotas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            foreignKey = @ForeignKey(name = "fk_incidencia_equipo")
    )
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(
            foreignKey = @ForeignKey(name = "fk_incidencia_ubicacion")
    )
    private Ubicacion ubicacion;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Incidencia that = (Incidencia) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
