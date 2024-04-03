package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.ImagenEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoDisponibleEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.entrada.modificacion.ProductoModificacionEntrada;
import com.dh.Xplorando.dto.salida.*;
import com.dh.Xplorando.entity.Caracteristica;
import com.dh.Xplorando.entity.Categoria;
import com.dh.Xplorando.entity.Imagen;
import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.CaracteristicaRepository;
import com.dh.Xplorando.repository.CategoriaRepository;
import com.dh.Xplorando.repository.ImagenRepository;
import com.dh.Xplorando.repository.ProductoRepository;
import com.dh.Xplorando.service.IProductoService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ImagenRepository imagenRepository;
    private final CaracteristicaRepository caracteristicaRepository;
    private final ModelMapper modelMapper;
    private final CategoriaService categoriaService;
    private final ImagenService imagenService;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ImagenRepository imagenRepository, CaracteristicaRepository caracteristicaRepository, ModelMapper modelMapper, CategoriaService categoriaService, ImagenService imagenService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.imagenRepository = imagenRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.modelMapper = modelMapper;
        this.categoriaService = categoriaService;
        this.imagenService = imagenService;
        configureMapping();
    }

    @Override
    public List<ProductoSalidaDto> listarProductos() {
        List<ProductoSalidaDto> productos = productoRepository.findAll().stream().map(this::entidadADto).toList();
        LOGGER.info("LISTA DE LOS PRODUCTOS DISPONIBLES : {}" + productos);
        return productos;
    }

    @Override
    public ProductoSalidaDto crearProducto(ProductoEntradaDto productoEntradaDto) throws BadRequestException, DataIntegrityViolationException, ResourceNotFoundException {

        String categoriaId = productoEntradaDto.getCategoriaString();
        Categoria categoria = categoriaRepository.findByNombreCategoria(categoriaId);
        if (categoria == null) {
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre proporcionado: " + categoriaId);
        }

        Set<Caracteristica> caracteristicasList = new HashSet<>();
        Set<String> arrayDeCaracteristicas = productoEntradaDto.getCaracteristica_nombre();
        for (String caracteristica : arrayDeCaracteristicas){
            Caracteristica caracteristicaBuscada = caracteristicaRepository.findByNombreCaracteristica(caracteristica);
            if (caracteristicaBuscada == null){
                LOGGER.error("No se encontró la caracteristica buscada");
                throw new ResourceNotFoundException("No se encontró la caracteristica en la base de datos: " + caracteristica);
            }
            caracteristicasList.add(caracteristicaBuscada);
        }

        Producto productoEntidad = modelMapper.map(productoEntradaDto, Producto.class);
        productoEntidad.setCategoria(categoria);
        productoEntidad.setCaracteristicas(caracteristicasList);


        List<Imagen> imagenesList = new ArrayList<>();
        for (ImagenEntradaDto imagenEntradaDto : productoEntradaDto.getImagenes()) {
            Imagen imagenEntidad = modelMapper.map(imagenEntradaDto, Imagen.class);
            LOGGER.info("Imagen: " + imagenEntidad);
            imagenEntidad.setProducto(productoEntidad);
            imagenesList.add(imagenEntidad);
        }
        productoEntidad.setImagenes(imagenesList);


        Producto productoCreado = productoRepository.save(productoEntidad);


        ProductoSalidaDto productoSalidaDto = entidadADto(productoCreado);
        LOGGER.info("Nuevo producto registrado con éxito: {}", productoSalidaDto);

        return productoSalidaDto;
    }


    @Override
    public ProductoSalidaDto editarProducto(ProductoModificacionEntrada productoModificacionEntradaDto) throws ResourceNotFoundException {
        LOGGER.info("PRODUCTO A MODIFICAR: " + productoModificacionEntradaDto);  //entra sin imágenes
        Long buscarProductoId = productoModificacionEntradaDto.getId();
        Optional<Producto> productoBuscado = productoRepository.findById(buscarProductoId);

        // Verificar si el producto buscado está presente
        if (!productoBuscado.isPresent()) {
            throw new ResourceNotFoundException("No se encontró el producto con el ID proporcionado: " + buscarProductoId);
        }
        LOGGER.info("PRODUCTO: " + productoBuscado);

        String categoriaTitulo = productoModificacionEntradaDto.getCategoriaString();
        Categoria categoria = categoriaRepository.findByNombreCategoria(categoriaTitulo);

        // Verificar si se encontró la categoría
        if (categoria == null) {
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre proporcionado: " + categoriaTitulo);
        }

        // Obtener el producto de la base de datos
        Producto productoExistente = productoBuscado.get();

        // Actualizar los campos del producto existente
        productoExistente.setCodigoProducto(productoModificacionEntradaDto.getCodigoProducto());
        productoExistente.setNombreProducto(productoModificacionEntradaDto.getNombreProducto());
        productoExistente.setDescripcionProducto(productoModificacionEntradaDto.getDescripcionProducto());
        productoExistente.setPrecioProducto(productoModificacionEntradaDto.getPrecioProducto());
        productoExistente.setUbicacion(productoModificacionEntradaDto.getUbicacion());
        productoExistente.setCategoria(categoria);  // Actualizar la categoría

        // Guardar el producto modificado
        Producto guardarProducto = productoRepository.save(productoExistente);
        LOGGER.info("PRODUCTO GUARDADO: " + guardarProducto);

        // Mapear el producto guardado a ProductoSalidaDto
        ProductoSalidaDto productoSalidaDto = entidadADto(guardarProducto);
        LOGGER.info("PRODUCTO SALIDA: " + productoSalidaDto);

        LOGGER.info("El producto " + productoExistente + " fue modificado exitosamente ");

        return productoSalidaDto;
    }


    @Override
    public void eliminarProductoPorId(Long id) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(id).orElse(null);
        if (productoBuscado !=null){
            productoRepository.deleteById(id);
            LOGGER.warn("Se eliminó el producto con id: " + productoBuscado);
        } else
            throw new ResourceNotFoundException("No se encontró el producto en la base de datos");
        LOGGER.error("No se encontró el producto en la base de datos");

    }

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id){
        Producto productoBuscado = productoRepository.findById(id).orElse(null);
        ProductoSalidaDto productoEncontrado = null;
        if (productoBuscado != null) {
            //cambie entidad a dto
            productoEncontrado = entidadADto(productoBuscado);
            LOGGER.info("Se ha encontrado el paquete: " + productoEncontrado);
        } else {
            LOGGER.error("No se ha encontrado en la BDD un paquete con ese id " + id);
        }
        return productoEncontrado;
    }

    @Override
    public ProductoSalidaDto buscarProductoPorNombre(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException {

            String nombreProducto = productoEntradaDto.getNombreProducto();
            Producto productoPorNombre = productoRepository.findByNombreProducto(nombreProducto);

            ProductoSalidaDto productoEncontrado = null;
            if (productoPorNombre!= null){
                productoEncontrado = entidadADto(productoPorNombre);
                LOGGER.info("Producto encontrado : " + productoPorNombre);
            } else{
                LOGGER.info("No se encontró el producto con el nombre : " + nombreProducto);
                throw new ResourceNotFoundException("No se encontró el producto con el nombre : " + nombreProducto);
            }

            return productoEncontrado;
        }

    @Override
    public ProductoSalidaDto buscarProductoPorUbicacion(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException {

        String ubicacion = productoEntradaDto.getUbicacion();
        Producto productoPorUbicacion = productoRepository.findByUbicacion(ubicacion);

        ProductoSalidaDto productoEncontrado = null;
        if (productoPorUbicacion!= null){
            productoEncontrado = entidadADto(productoPorUbicacion);
            LOGGER.info("Producto encontrado : " + productoPorUbicacion);
        } else{
            LOGGER.info("No se encontró el producto con la ubicación : " + ubicacion);
            throw new ResourceNotFoundException("No se encontró el producto con la ubicación : " + ubicacion);
        }

        return productoEncontrado;
    }

   /* private boolean buscadorProductoPorFecha(Producto producto, LocalDate fechaInicio, LocalDate fechaFinal){
        for( LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFinal); fecha = fecha.plusDays(1)){
            if( producto.getFechasReservadas().contains(fecha)){
                LOGGER.info("La fecha: "+ fecha+" hasta la fecha " + fechaFinal + " se encuentra reservada");
                return false; //el producto no está disponible para las fechas buscadas
            }
        }
        return true;
        }*/

    @Override
    public ProductoSalidaDto buscarProductoDisponible(ProductoDisponibleEntradaDto productoDisponibleEntradaDto) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findByUbicacion(productoDisponibleEntradaDto.getUbicacionP());
        LocalDate fechaInicio = productoDisponibleEntradaDto.getFechaInicio();
        LocalDate fechaFinal = productoDisponibleEntradaDto.getFechaFinal();

        ProductoSalidaDto productoDisponibleSalidaDto= null;

        List<LocalDate> fechaBuscada= new ArrayList<>();

        List<LocalDate>fechasReservadas= productoBuscado.getFechasReservadas();

        if (productoBuscado != null){
            if (fechaFinal.compareTo(fechaInicio) >= 2) {
                while (!fechaInicio.isAfter(fechaFinal)) {
                    fechaBuscada.add(fechaInicio);
                    fechaInicio = fechaInicio.plusDays(1);
                }
                for (LocalDate fecha : fechaBuscada) {
                    LOGGER.info("Fecha" + fecha);
                    if (fechasReservadas.contains(fecha)) {
                        LOGGER.error("la fecha" + fecha + "se encuentra reseravda");
                        throw new ResourceNotFoundException("La fecha" + fecha + "ya se encuentra reservada");
                    }
                }
                LOGGER.info("El producto se encuentra disponible para reservar en las fechas:" + fechaBuscada);
                productoDisponibleSalidaDto = entidadADto(productoBuscado);
            } else {
                LOGGER.error("La fecha de reserva debe ser mayor a 48hs");
                throw  new ResourceNotFoundException("La fecha de reserva debe ser mayor a 48hs");
            }
        }
        else {
            LOGGER.error("El producto no existe en la BDD");
            throw  new ResourceNotFoundException("El producto no existe en la BDD");
        }
        return productoDisponibleSalidaDto;
    }

    private void configureMapping(){
        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper ->
                {
                    mapper.map(Producto::getCategoria,ProductoSalidaDto::setCategoriaSalidaDto);
                    mapper.map(Producto::getCaracteristicas,ProductoSalidaDto::setCaracteristicaSalidaDtos);
                    mapper.map(Producto::getFechasReservadas,ProductoSalidaDto::setFechasReservadasSalidaDTo);
                });

    }


    private CategoriaProductoSalidaDto categoriaSalidaDtoASalidaProductoDto(Long id) {
        return modelMapper.map(categoriaService.buscarCategoriaPorId(id), CategoriaProductoSalidaDto.class);
    }

    private List<ImagenSalidaDto> imagenSalidaDtoASalidaProductoDto(List<Imagen> imagenList) {
        List<ImagenSalidaDto> imagenListDto = new ArrayList<ImagenSalidaDto>();

        for (Imagen imagen : imagenList) {
            ImagenSalidaDto imagenSalidaDto = modelMapper.map(imagen, ImagenSalidaDto.class);
            imagenListDto.add(imagenSalidaDto);
        }

        return imagenListDto;
    }

    public Producto dtoModificacionAentidad (ProductoModificacionEntrada productoModificacionEntrada){
        return modelMapper.map(productoModificacionEntrada,Producto.class);
    }

    private ProductoSalidaDto entidadADto(Producto producto) {
        ProductoSalidaDto productoSalidaDto = modelMapper.map(producto, ProductoSalidaDto.class);

        // Mapear la categoría si existe
        if (producto.getCategoria() != null) {
            CategoriaSalidaDto categoriaSalidaDto = modelMapper.map(producto.getCategoria(), CategoriaSalidaDto.class);
            productoSalidaDto.setCategoriaSalidaDto(categoriaSalidaDto);
        }

        // Mapear las características si existen
        if (!producto.getCaracteristicas().isEmpty()) {
            Set<CaracteristicaSalidaDto> caracteristicaSalidaDtos = producto.getCaracteristicas().stream()
                    .map(caracteristica -> modelMapper.map(caracteristica, CaracteristicaSalidaDto.class))
                    .collect(Collectors.toSet());
            productoSalidaDto.setCaracteristicaSalidaDtos(caracteristicaSalidaDtos);
        }



        // Mapear las imágenes si existen
        List<ImagenSalidaDto> imagenSalidaDtoList = producto.getImagenes().stream()
                .map(imagen -> modelMapper.map(imagen, ImagenSalidaDto.class))
                .collect(Collectors.toList());
        productoSalidaDto.setImagenSalidaDtoList(imagenSalidaDtoList);

        return productoSalidaDto;
    }

}


