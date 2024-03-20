package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.CategoriaEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.CategoriaSalidaDto;
import com.dh.Xplorando.dto.salida.ImagenSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.entity.Categoria;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface ICategoriaService {
    List<CategoriaSalidaDto> listarCategorias();

    CategoriaSalidaDto crearCategoria(CategoriaEntradaDto categoriaEntradaDto) throws BadRequestException;

    CategoriaSalidaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException;

    CategoriaSalidaDto buscarCategoriaPorNombre(CategoriaEntradaDto categoriaEntradaDto) throws ResourceNotFoundException;

}