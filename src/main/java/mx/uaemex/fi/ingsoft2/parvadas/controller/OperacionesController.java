package mx.uaemex.fi.ingsoft2.parvadas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.uaemex.fi.ingsoft2.parvadas.entity.*;
import mx.uaemex.fi.ingsoft2.parvadas.service.OperacionesService;

@RestController
@RequestMapping("/operaciones")
public class OperacionesController {

    @Autowired
    private OperacionesService operacionesService;

    // 1. Consulta de parvadas asignadas
    @GetMapping("/mis-parvadas/{idResponsable}")
    public List<ParvadaEntity> obtenerMisParvadas(@PathVariable("idResponsable") int idResponsable) {
        return operacionesService.listarAsignadas(idResponsable);
    }

    // 2. Registrar Vacunación
    @PostMapping("/vacunar")
    public ResponseEntity<VacunacionEntity> vacunar(@RequestBody VacunacionEntity vacunacion) {
        return new ResponseEntity<>(operacionesService.registrarVacunacion(vacunacion), HttpStatus.CREATED);
    }

    // 3. Registrar Medicación
    @PostMapping("/medicar")
    public ResponseEntity<MedicacionEntity> medicar(@RequestBody MedicacionEntity medicacion) {
        return new ResponseEntity<>(operacionesService.registrarMedicacion(medicacion), HttpStatus.CREATED);
    }

    // 4. Registrar Diario de Mortalidad
    @PostMapping("/mortalidad")
    public ResponseEntity<?> registrarMortalidad(@RequestBody MortalidadEntity mortalidad) {
        try {
            MortalidadEntity guardado = operacionesService.registrarMortalidad(mortalidad);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 5. Registrar Baja de aves por Salida
    @PostMapping("/salida")
    public ResponseEntity<?> registrarSalida(@RequestBody SalidaEntity salida) {
        try {
            SalidaEntity guardado = operacionesService.registrarSalida(salida);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}