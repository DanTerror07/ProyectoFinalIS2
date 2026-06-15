package mx.uaemex.fi.ingsoft2.parvadas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoAveEntity;
import mx.uaemex.fi.ingsoft2.parvadas.service.TipoAveService;

@RestController
@RequestMapping("/tipos-ave")
public class TipoAveController {

    @Autowired
    private TipoAveService tipoAveService;

    @GetMapping("/listar")
    public List<TipoAveEntity> listar() {
        return tipoAveService.listarTodos();
    }

    @PostMapping("/guardar")
    public ResponseEntity<TipoAveEntity> guardar(@RequestBody TipoAveEntity tipoAve) {
        return new ResponseEntity<>(tipoAveService.guardar(tipoAve), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAveEntity> buscarPorId(@PathVariable("id") int id) {
        return tipoAveService.buscarPorId(id)
                .map(tipo -> new ResponseEntity<>(tipo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        if (tipoAveService.eliminar(id)) {
            return ResponseEntity.ok("Tipo de ave eliminado con exito.");
        }
        return new ResponseEntity<>("Tipo de ave no encontrado.", HttpStatus.NOT_FOUND);
    }
}