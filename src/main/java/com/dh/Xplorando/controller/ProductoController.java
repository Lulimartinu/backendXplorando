package com.dh.Xplorando.controller;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.entrada.modificacion.ProductoModificacionEntrada;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
@CrossOrigin
//crossOrigin ?
public class ProductoController {
    //El controler es el responsable de manejar las solicitudes HTTP y enviar respuestas

    //es el artefacto principal de nuestros servicios REST

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductoSalidaDto> crearProducto(@Valid @RequestBody ProductoEntradaDto producto) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(productoService.crearProducto(producto), HttpStatus.CREATED);
    }
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductos() throws ResourceNotFoundException {
        return new ResponseEntity<>(productoService.listarProductos(),HttpStatus.OK);
    }

    @PutMapping("/editar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductoSalidaDto> editarProducto(@Valid @RequestBody ProductoModificacionEntrada producto) throws ResourceNotFoundException {
        return new ResponseEntity<>(productoService.editarProducto(producto), HttpStatus.OK);
    }
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String>eliminarProductoPorId(@PathVariable Long id ) throws ResourceNotFoundException {
        productoService.eliminarProductoPorId(id);
        return new ResponseEntity<>("Paquete eliminado correctamente", HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductoSalidaDto> buscarProductoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productoService.buscarProductoPorId(id), HttpStatus.OK);
    }


}

