package com.dh.Xplorando.controller;


import com.dh.Xplorando.dto.entrada.ReservaEntradaDto;
import com.dh.Xplorando.dto.salida.ReservaSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.service.IReservaService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reservas")
public class ReservaController {
    private final IReservaService iReservaService;

    public ReservaController(IReservaService iReservaService) {
        this.iReservaService = iReservaService;
    }
    @PostMapping("/crear")

    public ResponseEntity<ReservaSalidaDto> crearReserva(@Valid @RequestBody ReservaEntradaDto reservaEntradaDto) throws
            ResourceNotFoundException, ResourceNotFoundException, BadRequestException {
        return new ResponseEntity<>(iReservaService.crearReserva(reservaEntradaDto) , HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ReservaSalidaDto>> listarReservas(){
        return new ResponseEntity<>(iReservaService.listarReservas(), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String>eliminarReservaPorId(@PathVariable Long id ) throws ResourceNotFoundException {
        iReservaService.eliminarReservaPorId(id);
        return new ResponseEntity<>("Reserva eliminada correctamente", HttpStatus.NO_CONTENT);
    }


}