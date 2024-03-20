package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.CategoriaEntradaDto;
import com.dh.Xplorando.dto.salida.CategoriaSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.entity.Categoria;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.CategoriaRepository;
import com.dh.Xplorando.service.ICategoriaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {
    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoriaSalidaDto> listarCategorias() {


        List<CategoriaSalidaDto> categorias = categoriaRepository.findAll().stream().map(this::entidadAdtoSalida).toList();
        LOGGER.info("LISTA DE LAS CATEGORÍAS DISPONIBLES : {}", categorias);
        return categorias;

    }

    @Override
    public CategoriaSalidaDto crearCategoria(CategoriaEntradaDto categoriaEntradaDto)  {
        Categoria categoriaRecibida = dtoEntradaAentidad(categoriaEntradaDto);
        Categoria categoriaRegistrada = categoriaRepository.save(categoriaRecibida);
        CategoriaSalidaDto categoriaResultado = entidadAdtoSalida(categoriaRegistrada);
        LOGGER.info("Categoria registrada: " + categoriaRegistrada);
        return categoriaResultado;
    }

    @Override
    public CategoriaSalidaDto buscarCategoriaPorId(Long id) {
        Categoria categoriaBuscada = categoriaRepository.findById(id).orElse(null);
        CategoriaSalidaDto categoriaSalidaDto = null;

        if (categoriaBuscada != null) {
            categoriaSalidaDto = entidadAdtoSalida(categoriaBuscada);
            LOGGER.info("Se ha encontrado la Categoría: {}", categoriaSalidaDto);
        } else
        {LOGGER.error("No se ha encontrado una Categoria en la BDD con el id" + id);
        }

        return categoriaSalidaDto;
    }

    @Override
    public CategoriaSalidaDto buscarCategoriaPorNombre(CategoriaEntradaDto categoriaEntradaDto) throws ResourceNotFoundException {
        String nombreCategoria = categoriaEntradaDto.getNombreCategoria();
        Categoria categoriaPorNombre = categoriaRepository.findByNombreCategoria(nombreCategoria);

        CategoriaSalidaDto categoriaEncontrada = null;
        if(categoriaEncontrada!= null){
            categoriaEncontrada = entidadAdtoSalida(categoriaPorNombre);
            LOGGER.info("Categoría encontrada por nombre: " +categoriaPorNombre);
        } else{
            LOGGER.info("No se encontró la categoría con el nombre: " + nombreCategoria);
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre : " + nombreCategoria);
        }
        return categoriaEncontrada;
    }


    //MAPEO
    public Categoria dtoEntradaAentidad(CategoriaEntradaDto categoriaEntradaDto){
        return modelMapper.map(categoriaEntradaDto, Categoria.class);
    }

    public CategoriaSalidaDto entidadAdtoSalida(Categoria categoria){
        return modelMapper.map(categoria, CategoriaSalidaDto.class);
    }


}