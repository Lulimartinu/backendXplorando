package com.dh.Xplorando.dto.salida;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaProductoSalidaDto {
    private Long id;

    private String nombreCategoria;


}
