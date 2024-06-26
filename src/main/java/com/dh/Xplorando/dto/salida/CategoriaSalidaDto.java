package com.dh.Xplorando.dto.salida;


import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.entity.Producto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaSalidaDto {

    private Long id;

    private String nombreCategoria;

    private String imagenCategoria;

}