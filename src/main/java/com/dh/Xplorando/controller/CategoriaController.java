package com.dh.Xplorando.controller;

import com.dh.Xplorando.dto.entrada.CategoriaEntradaDto;
import com.dh.Xplorando.dto.salida.CategoriaSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.entity.Categoria;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.service.ICategoriaService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final ICategoriaService iCategoriaService;

    public CategoriaController(ICategoriaService iCategoriaService) {
        this.iCategoriaService = iCategoriaService;
    }

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriaSalidaDto> crearCategoria(@Valid @RequestBody CategoriaEntradaDto categoriaEntradaDto) throws BadRequestException {
        return new ResponseEntity<>(iCategoriaService.crearCategoria(categoriaEntradaDto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaSalidaDto>> listarCategorias() throws ResourceNotFoundException {
        return new ResponseEntity<>(iCategoriaService.listarCategorias(),HttpStatus.OK);
    }



}