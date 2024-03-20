package com.dh.Xplorando.controller;

import com.dh.Xplorando.dto.entrada.CaracteristicaEntradaDto;
import com.dh.Xplorando.dto.salida.CaracteristicaSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.service.ICaracteristicaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/caracteristicas")
@AllArgsConstructor
public class CaracteristicaController {

    private final ICaracteristicaService iCaracteristicaService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CaracteristicaSalidaDto> crearCaracteristica(@Valid @RequestBody CaracteristicaEntradaDto caracteristicaEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iCaracteristicaService.crearCaracteristica(caracteristicaEntradaDto) ,  HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CaracteristicaSalidaDto>> listarCaracteristicas(){
        return new ResponseEntity<>(iCaracteristicaService.listarCaracteristicas(), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> eliminarCaracteristica(@PathVariable Long id) throws ResourceNotFoundException {
        iCaracteristicaService.eliminarCaracteristica(id);
        return new ResponseEntity<>("caracteristica eliminada correctamente", HttpStatus.NO_CONTENT);
    }
}