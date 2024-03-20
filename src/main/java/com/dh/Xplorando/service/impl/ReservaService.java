package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.ReservaEntradaDto;
import com.dh.Xplorando.dto.salida.ReservaSalidaDto;
import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.entity.Reserva;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.ProductoRepository;
import com.dh.Xplorando.repository.ReservaRepository;
import com.dh.Xplorando.service.IReservaService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {
    private final Logger LOGGER = LoggerFactory.getLogger(ImagenService.class);
    private final ProductoRepository productoRepository;

    private final ReservaRepository reservaRepository;

    private final ModelMapper modelMapper;

    public ReservaService(ProductoRepository productoRepository, ReservaRepository reservaRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.reservaRepository = reservaRepository;
        this.modelMapper = modelMapper;
        //  configureMapping();
    }

    @Override
    public List<ReservaSalidaDto> listarReservas() {
        List<Reserva> reservas = reservaRepository.findAll();

        List<ReservaSalidaDto> reservasSalidaDtoList = new ArrayList<>();

        for (Reserva r : reservas) {
            ReservaSalidaDto reservaSalidaDto = entidadAdtoSalida(r);
            reservasSalidaDtoList.add(reservaSalidaDto);
        }
        LOGGER.info("Listado de todas las reservas : " + reservas);
        return reservasSalidaDtoList;
    }


    @Override
    public ReservaSalidaDto crearReserva(ReservaEntradaDto reservaEntradaDto) throws BadRequestException, ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(reservaEntradaDto.getProductoId()).orElse(null);

        ReservaSalidaDto reservaGuardadaDto = null;

        LocalDate fechaInicio= reservaEntradaDto.getFechaInicio();
        LocalDate fechaFinal= reservaEntradaDto.getFechaFinal();
        List<LocalDate> fechaBuscada= new ArrayList<>();


        List<LocalDate>fechasReservadas= productoBuscado.getFechasReservadas();
        if (productoBuscado != null){
            if (fechaFinal.compareTo(fechaInicio) >= 2){
                while (!fechaInicio.isAfter(fechaFinal)){
                    fechaBuscada.add(fechaInicio);
                    fechaInicio = fechaInicio.plusDays(1);
                }
                for (LocalDate fecha: fechaBuscada){
                    LOGGER.info("Fecha" + fecha);
                    if (fechasReservadas.contains(fecha)){
                        LOGGER.error("el paquete en la fecha " + fecha + " ya se encuentra reservado");
                        throw new ResourceNotFoundException("el paquete en la fecha" + fecha + "ya se encuentra reservado");
                    }else {
                        fechasReservadas.add(fecha);
                    }
                }

                Reserva reservaRecibida = dtoEntradaAentidad(reservaEntradaDto);
                reservaRecibida.setProducto(productoBuscado);

                Reserva reservaGuardada = reservaRepository.save(reservaRecibida);
                reservaGuardadaDto = entidadAdtoSalida(reservaGuardada);
                LOGGER.info("Reserva realizada con exito: " + reservaRecibida);
            }
            else {
                LOGGER.error("La fecha de reserva debe ser mayor a 48hs");
                throw  new ResourceNotFoundException("La fecha de reserva debe ser mayor a 48hs");
            }
        }
        else {
            LOGGER.error("El producto no existe en la BDD");
            throw  new ResourceNotFoundException("El producto no existe en la BDD");
        }
        return reservaGuardadaDto;
    }

    @Override
    public void eliminarReservaPorId(Long id) throws ResourceNotFoundException {
        Reserva reservaBuscada = reservaRepository.findById(id).orElse(null);

        if (reservaBuscada !=null){
            reservaRepository.deleteById(id);
            LOGGER.warn("Se eliminó la reserva con id: " + reservaBuscada);
        } else
            throw new ResourceNotFoundException("No se encontró la reserva en la base de datos");
        LOGGER.error("No se encontró la reserva en la base de datos");
    }

    @Override
    public ReservaSalidaDto buscarReservaPorId(Long id) throws ResourceNotFoundException {
        Reserva reservaBuscada= reservaRepository.findById(id).orElse(null);

        ReservaSalidaDto reservaEncontrada = null;
        if (reservaEncontrada != null){
            reservaEncontrada = entidadAdtoSalida(reservaBuscada);
            LOGGER.info("Reserva encontrada : " + reservaBuscada);
        }else {
            LOGGER.error("El id de la reseva no se encuentra en la base de datos");
            throw new ResourceNotFoundException("No se encontró la reserva en la base de datos");
        }

        return reservaEncontrada;
    }



    private void configureMapping(){
        modelMapper.typeMap(Producto.class, ReservaSalidaDto.class)
                .addMappings(mapper ->
                        mapper.map(Producto:: getNombreProducto, ReservaSalidaDto::setProductoNombre));
    }

    public Reserva dtoEntradaAentidad(ReservaEntradaDto reservaEntradaDto){
        return modelMapper.map(reservaEntradaDto, Reserva.class);
    }

    public ReservaSalidaDto entidadAdtoSalida(Reserva reserva){
        return modelMapper.map(reserva, ReservaSalidaDto.class);
    }


}