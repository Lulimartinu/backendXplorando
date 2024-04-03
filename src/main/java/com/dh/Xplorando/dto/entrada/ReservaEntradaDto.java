package com.dh.Xplorando.dto.entrada;

import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ReservaEntradaDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Ingrese una fecha correcta. No puede ingresar una fecha anterior al día de hoy")
    @NotNull(message = "Ingrese una fecha de inicio")
    private LocalDate fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Ingrese una fecha correcta. No puede ingresar una fecha anterior al día de hoy")
    @NotNull(message = "Ingrese una fecha final")
    private LocalDate fechaFinal;
    @NotNull(message = "Ingrese un Producto")
    private Long productoId;

    @NotNull(message = "Debe estar logueado a un usuario")
    private Long userId;


}
