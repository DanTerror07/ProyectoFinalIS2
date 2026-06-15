package mx.uaemex.fi.ingsoft2.parvadas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.uaemex.fi.ingsoft2.parvadas.entity.MortalidadEntity;
import mx.uaemex.fi.ingsoft2.parvadas.entity.VacunacionEntity;
import mx.uaemex.fi.ingsoft2.parvadas.service.OperacionesService;

@RestController
@RequestMapping("/operaciones")
public class OperacionesController {

    @Autowired
    private OperacionesService operacionesService; // Inyección directa corregida

    // Caso de Uso: Registrar Vacunación
    @PostMapping("/vacunar")
    public ResponseEntity<VacunacionEntity> vacunar(@RequestBody VacunacionEntity vacunacion) {
        return new ResponseEntity<>(operacionesService.registrarVacunacion(vacunacion), HttpStatus.CREATED);
    }

    // Caso de Uso: Registrar Diario de Mortalidad (Con lógica de descuento)
    @PostMapping("/mortalidad")
    public ResponseEntity<?> registrarMortalidad(@RequestBody MortalidadEntity mortalidad) {
        try {
            MortalidadEntity guardado = operacionesService.registrarMortalidad(mortalidad);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            // Retorna el mensaje de error si las bajas superan la cantidad de aves vivas
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}