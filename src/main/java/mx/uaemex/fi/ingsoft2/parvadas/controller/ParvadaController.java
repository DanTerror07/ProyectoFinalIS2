package mx.uaemex.fi.ingsoft2.parvadas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.uaemex.fi.ingsoft2.parvadas.entity.ParvadaEntity;
import mx.uaemex.fi.ingsoft2.parvadas.service.ParvadaService;

@RestController
@RequestMapping("/parvadas")
public class ParvadaController {

    @Autowired
    private ParvadaService parvadaService;

    @GetMapping("/listar")
    public List<ParvadaEntity> listar() {
        return parvadaService.listarTodas();
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody ParvadaEntity parvada) {
        try {
            ParvadaEntity guardada = parvadaService.guardar(parvada);
            return new ResponseEntity<>(guardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParvadaEntity> buscarPorId(@PathVariable("id") int id) {
        return parvadaService.buscarPorId(id)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") int id, @RequestBody ParvadaEntity datosNuevos) {
        try {
            ParvadaEntity actualizada = parvadaService.actualizar(id, datosNuevos);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ENDPOINT ESPECIALIZADO 1: Cambio de gallinero
    @PutMapping("/{id}/cambiar-gallinero/{idGallinero}")
    public ResponseEntity<?> cambiarGallinero(@PathVariable("id") int idParvada, @PathVariable("idGallinero") int idGallinero) {
        try {
            ParvadaEntity actualizada = parvadaService.cambiarGallinero(idParvada, idGallinero);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ENDPOINT ESPECIALIZADO 2: Cambio de responsable de parvada
    @PutMapping("/{id}/cambiar-responsable/{idResponsable}")
    public ResponseEntity<?> cambiarResponsable(@PathVariable("id") int idParvada, @PathVariable("idResponsable") int idResponsable) {
        try {
            ParvadaEntity actualizada = parvadaService.cambiarResponsable(idParvada, idResponsable);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> darDeBaja(@PathVariable("id") int id) {
        if (parvadaService.darDeBaja(id)) {
            return ResponseEntity.ok("Parvada dada de baja correctamente.");
        }
        return new ResponseEntity<>("Parvada no encontrada.", HttpStatus.NOT_FOUND);
    }
}