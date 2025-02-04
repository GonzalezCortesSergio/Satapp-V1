package com.salesianostriana.dam.satapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "deleted=false")
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @OneToMany(
            mappedBy = "categoriaPadre",
            fetch = FetchType.LAZY
    )
    @Setter(AccessLevel.NONE)
    private List<Categoria> categoriasHijas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            foreignKey = @ForeignKey(name = "fk_categoria_categoria_padre")
    )
    private Categoria categoriaPadre;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;

    public void addCategoriaHija(Categoria categoriaHija){
        categoriaHija.setCategoriaPadre(this);
        categoriasHijas.add(categoriaHija);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Categoria categoria = (Categoria) o;
        return getId() != null && Objects.equals(getId(), categoria.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
