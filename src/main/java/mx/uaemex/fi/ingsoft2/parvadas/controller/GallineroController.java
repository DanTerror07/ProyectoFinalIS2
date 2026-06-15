package mx.uaemex.fi.ingsoft2.parvadas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.uaemex.fi.ingsoft2.parvadas.entity.GallineroEntity;
import mx.uaemex.fi.ingsoft2.parvadas.service.GallineroService;

@RestController
@RequestMapping("/gallineros")
public class GallineroController {

    @Autowired
    private GallineroService gallineroService;

    @GetMapping("/listar")
    public List<GallineroEntity> listar() {
        return gallineroService.listarTodos();
    }

    @PostMapping("/guardar")
    public ResponseEntity<GallineroEntity> guardar(@RequestBody GallineroEntity gallinero) {
        return new ResponseEntity<>(gallineroService.guardar(gallinero), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GallineroEntity> buscarPorId(@PathVariable("id") int id) {
        return gallineroService.buscarPorId(id)
                .map(g -> new ResponseEntity<>(g, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint explícito para resolver la extensión: "Registrar sanitización de gallinero"
    @PutMapping("/sanitizar/{id}")
    public ResponseEntity<String> sanitizar(@PathVariable("id") int id) {
        if (gallineroService.registrarSanitizacion(id)) {
            return ResponseEntity.ok("Sanitizacion registrada correctamente para el gallinero.");
        }
        return new ResponseEntity<>("Gallinero no encontrado.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        if (gallineroService.eliminar(id)) {
            return ResponseEntity.ok("Gallinero eliminado con exito.");
        }
        return new ResponseEntity<>("Gallinero no encontrado.", HttpStatus.NOT_FOUND);
    }
}