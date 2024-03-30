package com.dh.Xplorando.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDisponibleEntradaDto {

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message ="Debe completar con un nombre" )
    private String nombreP;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Ingrese una fecha correcta. No puede ingresar una fecha anterior al día de hoy")
    @NotNull(message = "Ingrese una fecha de inicio")
    private LocalDate fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Ingrese una fecha correcta. No puede ingresar una fecha anterior al día de hoy")
    @NotNull(message = "Ingrese una fecha final")
    private LocalDate fechaFinal;
}
