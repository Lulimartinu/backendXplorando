package com.dh.Xplorando.dto.entrada.modificacion;

import com.dh.Xplorando.dto.entrada.CaracteristicaEntradaDto;
import com.dh.Xplorando.dto.entrada.ImagenEntradaDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ProductoModificacionEntrada {
    @NotNull()
    private Long id;

    @NotNull()
    private int codigoProducto;
    @NotNull()
    private String nombreProducto;

    @NotNull()
    private String descripcionProducto;

    @NotNull()
    private double precioProducto;

    @NotNull()
    private String direccion;

    private List<ImagenEntradaDto> imagenes;
    @NotNull(message = "El nombre de la categoría no puede ser nulo")
    @JsonProperty("categoria")
    private String categoriaString;

    @JsonProperty("caracteristicas")
    private Set<CaracteristicaEntradaDto> caracteristicaEntradaDtos = new HashSet<>();
}