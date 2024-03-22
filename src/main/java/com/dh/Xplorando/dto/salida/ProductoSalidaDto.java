package com.dh.Xplorando.dto.salida;


import com.dh.Xplorando.dto.entrada.CaracteristicaEntradaDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoSalidaDto {

    private Long id;

    private int codigoProducto;

    private String nombreProducto;

    private String descripcionProducto;

    private double precioProducto;

    private String ubicacion;

    private List<ImagenSalidaDto> imagenSalidaDtoList;

    @JsonProperty("categoria")
    private CategoriaSalidaDto categoriaSalidaDto;

    @JsonProperty("caracteristicas")
    private Set<CaracteristicaSalidaDto> caracteristicaSalidaDtos= new HashSet<>();
    private List<LocalDate> fechasReservadasSalidaDTo = new ArrayList<>();


}
