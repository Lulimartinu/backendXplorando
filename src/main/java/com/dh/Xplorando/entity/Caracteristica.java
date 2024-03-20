package com.dh.Xplorando.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CARACTERISTICAS")
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="NOMBRE")
    private String nombreCaracteristica;
    @Column(name="ICONO")
    private String icono;

    @JsonIgnore
    @ManyToMany(mappedBy = "caracteristicas",fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();

}