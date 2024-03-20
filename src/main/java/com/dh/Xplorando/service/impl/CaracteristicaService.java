package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.CaracteristicaEntradaDto;
import com.dh.Xplorando.dto.salida.CaracteristicaSalidaDto;
import com.dh.Xplorando.entity.Caracteristica;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.CaracteristicaRepository;
import com.dh.Xplorando.service.ICaracteristicaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaracteristicaService implements ICaracteristicaService {

    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private final CaracteristicaRepository caracteristicaRepository;
    private final ModelMapper modelMapper;

    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository, ModelMapper modelMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CaracteristicaSalidaDto> listarCaracteristicas() {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();
        List<CaracteristicaSalidaDto> caracteristicaSalidaDtosList= new ArrayList<>();

        for (Caracteristica car: caracteristicas){
            CaracteristicaSalidaDto caracteristicaSalidaDto = entidadAdtoSalida(car);
            caracteristicaSalidaDtosList.add(caracteristicaSalidaDto);
        }
        LOGGER.info("Listado de todas las caracteristicas:" + caracteristicas);
        return caracteristicaSalidaDtosList;
    }

    @Override
    public CaracteristicaSalidaDto crearCaracteristica(CaracteristicaEntradaDto caracteristicaEntradaDto) throws ResourceNotFoundException {
        if (caracteristicaRepository.findByNombreCaracteristica(caracteristicaEntradaDto.getNombreCaracteristica())!=null){
            LOGGER.info("Existe una categoria con ese mismo nombre :" + caracteristicaRepository.findByNombreCaracteristica(caracteristicaEntradaDto.getNombreCaracteristica()));
            throw new ResourceNotFoundException("Existe una caracteristica con el mismo nombre");
        }
        Caracteristica caracteristicaRecibida= dtoEntradaAentidad(caracteristicaEntradaDto);
        Caracteristica caracteristicaRegistrada= caracteristicaRepository.save(caracteristicaRecibida);
        CaracteristicaSalidaDto caracteristicaResultado= entidadAdtoSalida(caracteristicaRegistrada);
        LOGGER.info("Caracteristica registrada: " + caracteristicaRegistrada);
        return caracteristicaResultado;
    }

    @Override
    public CaracteristicaSalidaDto buscarCaracteristicaPorId(Long id) {
        Caracteristica caracteristicaBuscada = caracteristicaRepository.findById(id).orElse(null);

        CaracteristicaSalidaDto caracteristicaEncontrada = null;
        if (caracteristicaBuscada!= null){
            caracteristicaEncontrada= entidadAdtoSalida(caracteristicaBuscada);
            LOGGER.info("Caracteristica encontrada: " + caracteristicaBuscada);
        }else
            LOGGER.error("El id de la caracteristica no se encuentra en la base de datos");

        return caracteristicaEncontrada;
    }

    @Override
    public void eliminarCaracteristica(Long id) throws ResourceNotFoundException {
        if (buscarCaracteristicaPorId(id) != null) {
            LOGGER.warn("Se eliminó la caracteristica con el id : " + (buscarCaracteristicaPorId(id)));
            caracteristicaRepository.deleteById(id);
        } else {
            LOGGER.error("No se encontró la caracteristica con el id : " + id);
            throw new ResourceNotFoundException("No se ha encontrado la caracteristica con id " + id);
        }
    };


    //MAPEO
    private Caracteristica dtoEntradaAentidad(CaracteristicaEntradaDto caracteristicaEntradaDto) {

        return modelMapper.map(caracteristicaEntradaDto, Caracteristica.class);
    }

    private CaracteristicaSalidaDto entidadAdtoSalida(Caracteristica caracteristica){
        return modelMapper.map(caracteristica, CaracteristicaSalidaDto.class);
    }

}
