package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.ImagenEntradaDto;
import com.dh.Xplorando.dto.salida.ImagenSalidaDto;
import com.dh.Xplorando.entity.Imagen;
import com.dh.Xplorando.repository.ImagenRepository;
import com.dh.Xplorando.service.IImagenService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagenService implements IImagenService {

    private final Logger LOGGER = LoggerFactory.getLogger(ImagenService.class);

    private final ImagenRepository imagenRepository;

    private final ModelMapper modelMapper;

    public ImagenService(ImagenRepository imagenRepository, ModelMapper modelMapper) {
        this.imagenRepository = imagenRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ImagenSalidaDto crearImagen(ImagenEntradaDto imagen) {
        Imagen imagenGuardada = imagenRepository.save(dtoEntradaAentidad((imagen)));
        ImagenSalidaDto imagenSalidaDto = entidadADtoSalida(imagenGuardada);
        LOGGER.info("Se ha creado una imagen con éxito ", imagenSalidaDto);
        return  imagenSalidaDto ;}

    @Override
    public List<ImagenSalidaDto> listarImagenes() {
        List<Imagen> imagenes = imagenRepository.findAll();
        List<ImagenSalidaDto> imagenSalidaDtoList= new ArrayList<>();

        for (Imagen img: imagenes){

            ImagenSalidaDto imagenSalidaDto = entidadADtoSalida(img);
            imagenSalidaDtoList.add(imagenSalidaDto);
        }
        LOGGER.info("Listado de todos las imagenes : " + imagenes);

        return imagenSalidaDtoList;
    }

    @Override
    public ImagenSalidaDto buscarImagenPorId(Long id) {
        Imagen imagenBuscada = imagenRepository.findById(id).orElse(null);
        ImagenSalidaDto imagenSalidaDto = null;

        if (imagenBuscada != null) {
            imagenSalidaDto = entidadADtoSalida(imagenBuscada);
            LOGGER.info("Se ha encontrado la Imagen: {}", imagenSalidaDto);
        } else
        {LOGGER.error("No se ha encontrado una Imagen en la BDD con el id" + id);
        }

        return imagenSalidaDto;
    }


              /*  dtoEntradaAentidad(imagenEntradaDto);
        Imagen imagenCreada = imagenRepository.save(imagenNueva);
        ImagenSalidaDto imagenSalidaDto = entidadADtoSalida(imagenCreada);
        LOGGER.info("Se ha creado una imagen con éxito ", imagenSalidaDto);
        return imagenSalidaDto;
    }*/


    //MAPEO
    private Imagen dtoEntradaAentidad(ImagenEntradaDto imagenEntradaDto) {
        return modelMapper.map(imagenEntradaDto, Imagen.class);
    }

    private ImagenSalidaDto entidadADtoSalida(Imagen imagen) {
        return modelMapper.map(imagen, ImagenSalidaDto.class);
    }

}