package com.dh.Xplorando.dto.entrada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaracteristicaEntradaDto {

    @NotNull(message = "El nombre de la característica no puede ser nulo")
    @Size(min = 1, max = 250)
    private String nombreCaracteristica;

    @NotNull(message = "la caracteristica debe tener un icono")
    @Size(min = 1, max = 250)
    private String icono;


}