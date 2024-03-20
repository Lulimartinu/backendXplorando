package com.dh.Xplorando.dto.salida;

import com.dh.Xplorando.entity.Producto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaracteristicaSalidaDto {

    private Long id;

    private String nombreCaracteristica;

    private String icono;


}