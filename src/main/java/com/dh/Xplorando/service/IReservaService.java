package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.entrada.ReservaEntradaDto;
import com.dh.Xplorando.dto.entrada.modificacion.ProductoModificacionEntrada;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.dto.salida.ReservaSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IReservaService {

    List<ReservaSalidaDto> listarReservas();
    ReservaSalidaDto crearReserva(ReservaEntradaDto reservaEntradaDto) throws BadRequestException, ResourceNotFoundException;
    void eliminarReservaPorId(Long id) throws ResourceNotFoundException;
    ReservaSalidaDto buscarReservaPorId(Long id)throws ResourceNotFoundException;

}
