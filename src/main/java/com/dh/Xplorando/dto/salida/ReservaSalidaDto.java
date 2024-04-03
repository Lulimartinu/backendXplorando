package com.dh.Xplorando.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaSalidaDto {


    private Long id;
    private LocalDate fechaInicio;

    private LocalDate fechaFinal;

    @JsonProperty("producto")
    private String nombreP;

    @JsonProperty("user")
    private String userFirstName;

}
