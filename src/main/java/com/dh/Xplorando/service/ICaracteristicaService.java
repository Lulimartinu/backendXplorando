package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.CaracteristicaEntradaDto;
import com.dh.Xplorando.dto.salida.CaracteristicaSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICaracteristicaService {
    List<CaracteristicaSalidaDto> listarCaracteristicas();
    CaracteristicaSalidaDto crearCaracteristica(CaracteristicaEntradaDto caracteristicaEntradaDto) throws ResourceNotFoundException;
    CaracteristicaSalidaDto buscarCaracteristicaPorId(Long id) ;

    void eliminarCaracteristica (Long id) throws ResourceNotFoundException;
}
