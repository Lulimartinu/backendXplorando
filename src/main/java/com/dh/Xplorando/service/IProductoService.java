package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.entrada.modificacion.ProductoModificacionEntrada;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IProductoService {
    List<ProductoSalidaDto> listarProductos();
    ProductoSalidaDto crearProducto(ProductoEntradaDto producto) throws BadRequestException, ResourceNotFoundException;

    ProductoSalidaDto editarProducto(ProductoModificacionEntrada productoModificacionEntrada) throws ResourceNotFoundException;


    void eliminarProductoPorId(Long id) throws ResourceNotFoundException;
    ProductoSalidaDto buscarProductoPorId(Long id)throws ResourceNotFoundException;


}