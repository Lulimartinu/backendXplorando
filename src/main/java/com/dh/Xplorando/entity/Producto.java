package com.dh.Xplorando.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PRODUCTOS")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="CODIGO", unique = true)
    private int codigoProducto;
    @Column(name="NOMBRE",unique = true)
    private String nombreProducto;
    @Column(name="DESCRIPCION")
    private String descripcionProducto;
    @Column(name="PRECIO")
    private double precioProducto;
    @Column(name="UBICACION")
    private String ubicacion;

    @OneToMany(mappedBy ="producto", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Imagen> imagenes ;

    @ManyToOne // (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id") //referencedColumnName = "id"
    private Categoria categoria;

    //The owner side is where we configure the relationship.
    //We can do this with the @JoinTable annotation
    //https://www.baeldung.com/jpa-many-to-many
    //On the target side, we only have to provide the name of the field, which maps the relationship.
    //merge --> fusiona,une el estado de el objeto dado en la otra entidad tmb

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name="productos_caracteristicas",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id")
    )

    //quiero añadir caracteristicas a esta lista
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "producto",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Reserva> reservas =new HashSet<>();
    private List<LocalDate> fechasReservadas = new ArrayList<>();


}
