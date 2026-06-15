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
    public ResponseEntity<ParvadaEntity> guardar(@RequestBody ParvadaEntity parvada) {
        return new ResponseEntity<>(parvadaService.guardar(parvada), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParvadaEntity> buscarPorId(@PathVariable("id") int id) {
        return parvadaService.buscarPorId(id)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> darDeBaja(@PathVariable("id") int id) {
        if (parvadaService.darDeBaja(id)) {
            return ResponseEntity.ok("Parvada dada de baja correctamente.");
        }
        return new ResponseEntity<>("Parvada no encontrada.", HttpStatus.NOT_FOUND);
    }
}