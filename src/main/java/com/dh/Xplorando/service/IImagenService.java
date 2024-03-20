package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.ImagenEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.ImagenSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.service.impl.ImagenService;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IImagenService {
    List<ImagenSalidaDto> listarImagenes();

    ImagenSalidaDto crearImagen(ImagenEntradaDto imagenEntradaDto) ;


    ImagenSalidaDto buscarImagenPorId(Long id);
}